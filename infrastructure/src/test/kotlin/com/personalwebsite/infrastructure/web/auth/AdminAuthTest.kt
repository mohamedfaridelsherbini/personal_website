package com.personalwebsite.infrastructure.web.auth

import io.ktor.client.request.get
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.testing.testApplication
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class AdminAuthTest {
    private val env =
        mapOf(
            "GOOGLE_CLIENT_ID" to "client-id",
            "GOOGLE_CLIENT_SECRET" to "client-secret",
            "ADMIN_GOOGLE_EMAIL" to "owner@example.com",
            "SESSION_SECRET" to "0123456789abcdef0123",
        )

    private fun adminAuth() = AdminAuth(requireNotNull(GoogleAdminConfig.fromEnv("https://site.test") { env[it] }))

    @Test
    fun configRequiresEveryValue() {
        assertNotNull(GoogleAdminConfig.fromEnv("https://site.test") { env[it] })
        env.keys.forEach { missing ->
            assertNull(GoogleAdminConfig.fromEnv("https://site.test") { if (it == missing) null else env[it] }, "$missing is required")
        }
        assertNull(GoogleAdminConfig.fromEnv("https://site.test") { if (it == "SESSION_SECRET") "short" else env[it] })
    }

    @Test
    fun onlyTheVerifiedAllowedEmailPasses() {
        assertTrue(isAllowedAdmin("Owner@Example.com", true, "owner@example.com"))
        assertFalse(isAllowedAdmin("owner@example.com", false, "owner@example.com"))
        assertFalse(isAllowedAdmin("owner@example.com", null, "owner@example.com"))
        assertFalse(isAllowedAdmin("other@example.com", true, "owner@example.com"))
        assertFalse(isAllowedAdmin(null, true, "owner@example.com"))
    }

    @Test
    fun anonymousVisitorsAreSentToGoogleSignIn() =
        testApplication {
            val adminAuth = adminAuth()
            application {
                installAdminAuth(adminAuth)
                routing {
                    adminAuthRoutes(adminAuth)
                    authenticate(ADMIN_SESSION_AUTH) { get("/admin") { call.respondText("secret") } }
                }
            }
            val noRedirects = createClient { followRedirects = false }

            val admin = noRedirects.get("/admin")
            assertEquals(HttpStatusCode.Found, admin.status)
            assertEquals("/admin/login", admin.headers[HttpHeaders.Location])

            val login = noRedirects.get("/admin/login")
            assertEquals(HttpStatusCode.Found, login.status)
            val location = login.headers[HttpHeaders.Location].orEmpty()
            assertTrue(location.startsWith("https://accounts.google.com/o/oauth2/v2/auth"), location)
            assertTrue(location.contains("client_id=client-id"), location)
            assertTrue(location.contains("redirect_uri=https%3A%2F%2Fsite.test%2Fadmin%2Fauth%2Fcallback"), location)
        }
}
