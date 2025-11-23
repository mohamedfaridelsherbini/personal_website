package com.personalwebsite

import com.personalwebsite.application.website.WebsiteQueries
import com.personalwebsite.di.appModule
import com.personalwebsite.infrastructure.admin.AdminContentService
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import com.personalwebsite.infrastructure.web.routing.registerRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.application.log
import io.ktor.server.auth.Authentication
import io.ktor.server.auth.UserIdPrincipal
import io.ktor.server.auth.basic
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
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
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

    val adminUser = System.getenv("ADMIN_USER")
    val adminPassword = System.getenv("ADMIN_PASSWORD")
    val adminAuthEnabled = !adminUser.isNullOrBlank() && !adminPassword.isNullOrBlank()
    val siteBaseUrl = System.getenv("SITE_BASE_URL") ?: "https://www.mohamedfaridelsherbini.com"

    if (adminAuthEnabled) {
        install(Authentication) {
            basic("adminAuth") {
                realm = "Admin panel"
                validate { credentials ->
                    if (credentials.name == adminUser && credentials.password == adminPassword) {
                        UserIdPrincipal(credentials.name)
                    } else {
                        null
                    }
                }
            }
        }
    } else {
        log.warn("Admin auth disabled. Set ADMIN_USER and ADMIN_PASSWORD to protect /admin")
    }

    registerRoutes(
        websiteQueries = websiteQueries,
        adminContentService = adminContentService,
        adminAuthEnabled = adminAuthEnabled,
        getPersonalProjectsUseCase = getPersonalProjectsUseCase,
        siteBaseUrl = siteBaseUrl,
    )
}
