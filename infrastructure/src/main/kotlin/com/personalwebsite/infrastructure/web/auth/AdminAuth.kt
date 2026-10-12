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
    authenticate(GOOGLE_OAUTH) {
        get("/admin/login") { /* Ktor redirects to Google */ }
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
