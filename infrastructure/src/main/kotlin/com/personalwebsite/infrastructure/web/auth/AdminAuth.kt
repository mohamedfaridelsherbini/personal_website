@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.auth

import com.google.gson.JsonParser
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.bearerAuth
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.Application
import io.ktor.server.application.call
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.OAuthAccessTokenResponse
import io.ktor.server.auth.OAuthServerSettings
import io.ktor.server.auth.Principal
import io.ktor.server.auth.authenticate
import io.ktor.server.auth.oauth
import io.ktor.server.auth.principal
import io.ktor.server.auth.session
import io.ktor.server.html.respondHtml
import io.ktor.server.plugins.forwardedheaders.XForwardedHeaders
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.SessionTransportTransformerMessageAuthentication
import io.ktor.server.sessions.Sessions
import io.ktor.server.sessions.clear
import io.ktor.server.sessions.cookie
import io.ktor.server.sessions.sessions
import io.ktor.server.sessions.set
import io.ktor.util.StatelessHmacNonceManager
import kotlinx.html.HTML
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.img
import kotlinx.html.link
import kotlinx.html.main
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.span
import kotlinx.html.title
import kotlinx.html.unsafe
import java.security.MessageDigest

const val ADMIN_SESSION_AUTH = "adminSession"
private const val GOOGLE_OAUTH = "google-oauth"

/** Firebase Hosting forwards only the cookie named `__session` to Cloud Run, so the session must use it. */
private const val SESSION_COOKIE = "__session"
private const val SESSION_MAX_AGE_SECONDS = 8 * 60 * 60L
private const val MIN_SECRET_LENGTH = 16

data class AdminSession(val email: String) : Principal

/**
 * Google sign-in settings for the admin panel, read from the environment.
 * Admin stays disabled (404) unless every value is present.
 */
data class GoogleAdminConfig(
    val clientId: String,
    val clientSecret: String,
    val allowedEmail: String,
    val sessionSecret: String,
    val baseUrl: String,
) {
    val callbackUrl: String get() = "${baseUrl.trimEnd('/')}/admin/auth/callback"

    companion object {
        fun fromEnv(
            baseUrl: String,
            env: (String) -> String? = System::getenv,
        ): GoogleAdminConfig? {
            fun value(name: String) = env(name)?.trim()?.takeIf { it.isNotEmpty() }
            val secret = value("SESSION_SECRET")?.takeIf { it.length >= MIN_SECRET_LENGTH }
            return GoogleAdminConfig(
                clientId = value("GOOGLE_CLIENT_ID") ?: return null,
                clientSecret = value("GOOGLE_CLIENT_SECRET") ?: return null,
                allowedEmail = value("ADMIN_GOOGLE_EMAIL") ?: return null,
                sessionSecret = secret ?: return null,
                baseUrl = baseUrl,
            )
        }
    }
}

/** Only the verified Google account named in ADMIN_GOOGLE_EMAIL may sign in. */
fun isAllowedAdmin(
    email: String?,
    emailVerified: Boolean?,
    allowedEmail: String,
): Boolean = emailVerified == true && email != null && email.equals(allowedEmail, ignoreCase = true)

class AdminAuth(val config: GoogleAdminConfig) {
    val client: HttpClient = HttpClient(CIO)
    internal val key: ByteArray = MessageDigest.getInstance("SHA-256").digest(config.sessionSecret.toByteArray())
}

fun Application.installAdminAuth(adminAuth: AdminAuth) {
    val config = adminAuth.config
    // Cloud Run terminates TLS and forwards plain HTTP; without this Ktor refuses to set the Secure session cookie.
    install(XForwardedHeaders)
    install(Sessions) {
        cookie<AdminSession>(SESSION_COOKIE) {
            cookie.path = "/"
            cookie.httpOnly = true
            cookie.secure = config.baseUrl.startsWith("https://")
            cookie.maxAgeInSeconds = SESSION_MAX_AGE_SECONDS
            cookie.extensions["SameSite"] = "Lax"
            transform(SessionTransportTransformerMessageAuthentication(adminAuth.key))
        }
    }
    install(Authentication) {
        oauth(GOOGLE_OAUTH) {
            client = adminAuth.client
            urlProvider = { config.callbackUrl }
            providerLookup = {
                OAuthServerSettings.OAuth2ServerSettings(
                    name = "google",
                    authorizeUrl = "https://accounts.google.com/o/oauth2/v2/auth",
                    accessTokenUrl = "https://oauth2.googleapis.com/token",
                    requestMethod = HttpMethod.Post,
                    clientId = config.clientId,
                    clientSecret = config.clientSecret,
                    defaultScopes = listOf("openid", "email"),
                    extraAuthParameters = listOf("prompt" to "select_account"),
                    nonceManager = StatelessHmacNonceManager(adminAuth.key),
                )
            }
        }
        session<AdminSession>(ADMIN_SESSION_AUTH) {
            validate { it }
            challenge { call.respondRedirect("/admin/login") }
        }
    }
}

fun Route.adminAuthRoutes(adminAuth: AdminAuth) {
    get("/admin/login") {
        call.respondHtml(HttpStatusCode.OK) { loginPage() }
    }
    authenticate(GOOGLE_OAUTH) {
        get("/admin/auth/start") { /* Ktor redirects to Google */ }
        get("/admin/auth/callback") {
            val token = call.principal<OAuthAccessTokenResponse.OAuth2>()?.accessToken
            if (token == null) {
                call.respondRedirect("/admin/login")
                return@get
            }
            val info =
                runCatching {
                    val body =
                        adminAuth.client.get("https://openidconnect.googleapis.com/v1/userinfo") {
                            bearerAuth(token)
                        }.bodyAsText()
                    JsonParser.parseString(body).asJsonObject
                }.getOrNull()
            val email = info?.get("email")?.asString
            val verified = info?.get("email_verified")?.asBoolean
            if (isAllowedAdmin(email, verified, adminAuth.config.allowedEmail)) {
                call.sessions.set(AdminSession(requireNotNull(email)))
                call.respondRedirect("/admin")
            } else {
                call.application.log.warn("Rejected admin sign-in attempt")
                call.respondText("This Google account is not allowed to use the admin panel.", status = HttpStatusCode.Forbidden)
            }
        }
    }
    post("/admin/logout") {
        call.sessions.clear<AdminSession>()
        call.respondRedirect("/")
    }
}

private const val GOOGLE_ICON =
    """<svg viewBox="0 0 48 48" width="20" height="20" aria-hidden="true" focusable="false">""" +
        """<path fill="#EA4335" d="M24 9.5c3.54 0 6.71 1.22 9.21 3.6l6.85-6.85C35.9 2.38 30.47 0 24 0 14.62 0 6.51 5.38 2.56 13.22l7.98 6.19C12.43 13.72 17.74 9.5 24 9.5z"/>""" +
        """<path fill="#4285F4" d="M46.98 24.55c0-1.57-.15-3.09-.38-4.55H24v9.02h12.94c-.58 2.96-2.26 5.48-4.78 7.18l7.73 6c4.51-4.18 7.09-10.36 7.09-17.65z"/>""" +
        """<path fill="#FBBC05" d="M10.53 28.59c-.48-1.45-.76-2.99-.76-4.59s.27-3.14.76-4.59l-7.98-6.19C.92 16.46 0 20.12 0 24c0 3.88.92 7.54 2.56 10.78l7.97-6.19z"/>""" +
        """<path fill="#34A853" d="M24 48c6.48 0 11.93-2.13 15.89-5.81l-7.73-6c-2.15 1.45-4.92 2.3-8.16 2.3-6.26 0-11.57-4.22-13.47-9.91l-7.98 6.19C6.51 42.62 14.62 48 24 48z"/></svg>"""

private fun HTML.loginPage() {
    head {
        title("Admin sign-in")
        meta { charset = "UTF-8" }
        meta(name = "viewport", content = "width=device-width, initial-scale=1")
        meta(name = "robots", content = "noindex")
        link(rel = "icon", href = "/static/images/favicon-portrait.png", type = "image/png")
        link(rel = "stylesheet", href = "/static/css/style.css")
    }
    body {
        main(classes = "admin-login") {
            div("admin-login-card") {
                img(src = "/static/images/logo-badge.svg", alt = "") { classes = setOf("admin-login-logo") }
                h1 { +"Admin sign-in" }
                p { +"Sign in with the Google account that manages this site." }
                a(href = "/admin/auth/start", classes = "ds-button ds-button--primary admin-login-button") {
                    unsafe { raw(GOOGLE_ICON) }
                    span { +"Sign in with Google" }
                }
                a(href = "/", classes = "admin-login-back") { +"Back to the site" }
            }
        }
    }
}
