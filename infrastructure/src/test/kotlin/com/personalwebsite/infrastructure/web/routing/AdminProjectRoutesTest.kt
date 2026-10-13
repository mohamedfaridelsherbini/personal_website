package com.personalwebsite.infrastructure.web.routing

import com.google.gson.Gson
import com.personalwebsite.infrastructure.admin.AdminContentService
import com.personalwebsite.infrastructure.admin.AdminProjectService
import com.personalwebsite.infrastructure.cache.InMemoryRenderCache
import com.personalwebsite.infrastructure.content.ContentLoader
import com.personalwebsite.infrastructure.web.auth.AdminAuth
import com.personalwebsite.infrastructure.web.auth.AdminSession
import com.personalwebsite.infrastructure.web.auth.GoogleAdminConfig
import com.personalwebsite.infrastructure.web.auth.installAdminAuth
import io.ktor.client.plugins.cookies.HttpCookies
import io.ktor.client.request.forms.submitForm
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.parameters
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import io.ktor.server.testing.testApplication
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class AdminProjectRoutesTest {
    @Test
    fun addAndEditProjectsThroughTheAdminPages() =
        testApplication {
            val dir = Files.createTempDirectory("admin-routes")
            listOf("personal-info", "skills", "work-experience", "education", "languages").forEach {
                Files.writeString(dir.resolve("$it.json"), "[]")
            }
            Files.writeString(
                dir.resolve("personal-projects.json"),
                """[{"name":"Alpha","slug":"alpha","description":"A","techStack":"Kotlin","category":"App"}]""",
            )
            val gson = Gson()
            val content = AdminContentService(gson, ContentLoader(gson), InMemoryRenderCache(), dir, Files.createTempDirectory("resume"))
            val adminAuth = AdminAuth(GoogleAdminConfig("id", "secret", "owner@example.com", "0123456789abcdef0123", "http://localhost"))
            application {
                installAdminAuth(adminAuth)
                routing {
                    get("/dev-login") {
                        call.sessions.set(AdminSession("owner@example.com"))
                        call.respondText("ok")
                    }
                    adminRoutes(content, AdminProjectService(content))
                }
            }
            val client =
                createClient {
                    install(HttpCookies)
                    followRedirects = false
                }

            assertEquals(HttpStatusCode.Found, client.get("/admin").status)
            client.get("/dev-login")

            val list = client.get("/admin").bodyAsText()
            assertTrue(list.contains("Manage your work") && list.contains("Alpha"), list)

            val invalid = client.submitForm("/admin/projects", parameters { append("slug", "alpha") })
            assertEquals(HttpStatusCode.UnprocessableEntity, invalid.status)
            assertTrue(invalid.bodyAsText().contains("This slug is already in use."))

            val created =
                client.submitForm(
                    "/admin/projects",
                    parameters {
                        append("name", "Beta")
                        append("slug", "beta")
                        append("placement", "master")
                        append("description", "B project")
                        append("linkLabel0", "Live")
                        append("linkUrl0", "https://beta.example.com")
                    },
                )
            assertEquals(HttpStatusCode.Found, created.status)
            assertEquals("/admin?saved=created&slug=beta&placement=master", created.headers[HttpHeaders.Location])

            val edit = client.get("/admin/projects/beta/edit").bodyAsText()
            assertTrue(edit.contains("Edit Beta") && edit.contains("https://beta.example.com"), edit)

            val updated =
                client.submitForm(
                    "/admin/projects/beta",
                    parameters {
                        append("name", "Beta 2")
                        append("placement", "normal")
                        append("description", "B project")
                    },
                )
            assertEquals("/admin?saved=updated&slug=beta&placement=normal", updated.headers[HttpHeaders.Location])
            assertTrue(client.get("/admin").bodyAsText().contains("Beta 2"))
            assertTrue(client.get("/admin/content").bodyAsText().contains("Content &amp; résumé"))
        }
}
