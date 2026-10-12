package com.personalwebsite

import com.personalwebsite.application.website.WebsiteQueries
import com.personalwebsite.di.appModule
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import com.personalwebsite.infrastructure.admin.AdminContentService
import com.personalwebsite.infrastructure.web.auth.AdminAuth
import com.personalwebsite.infrastructure.web.auth.GoogleAdminConfig
import com.personalwebsite.infrastructure.web.auth.installAdminAuth
import com.personalwebsite.infrastructure.web.routing.registerRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.ext.inject
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

/**
 * Main application entry point
 * Started with a simple setup, then added Koin for DI to reduce boilerplate
 */
fun main() {
    val port = System.getenv("PORT")?.toIntOrNull() ?: 8080
    embeddedServer(Netty, port = port, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // Install Koin for dependency injection (learned this from a tutorial)
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }

    // Get controller from Koin - much cleaner than manual DI
    val websiteQueries by inject<WebsiteQueries>()
    val adminContentService by inject<AdminContentService>()
    val getPersonalProjectsUseCase by inject<GetPersonalProjectsUseCase>()

    val siteBaseUrl = System.getenv("SITE_BASE_URL") ?: "https://www.mohamedfaridelsherbini.com"
    val adminAuth = GoogleAdminConfig.fromEnv(siteBaseUrl)?.let(::AdminAuth)

    if (adminAuth != null) {
        installAdminAuth(adminAuth)
    } else {
        log.warn(
            "Admin disabled. Set GOOGLE_CLIENT_ID, GOOGLE_CLIENT_SECRET, ADMIN_GOOGLE_EMAIL and SESSION_SECRET to enable /admin",
        )
    }

    registerRoutes(
        websiteQueries = websiteQueries,
        adminContentService = adminContentService,
        adminAuth = adminAuth,
        getPersonalProjectsUseCase = getPersonalProjectsUseCase,
        siteBaseUrl = siteBaseUrl,
    )
}
