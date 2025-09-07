package com.personalwebsite

import com.personalwebsite.di.appModule
import com.personalwebsite.presentation.controllers.WebsiteController
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.html.*
import io.ktor.server.http.content.*
import kotlinx.html.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.ktor.ext.inject
import mu.KotlinLogging

/**
 * Main application entry point
 * Follows Single Responsibility Principle - only handles application setup and routing
 * Follows Dependency Inversion Principle - uses Koin for dependency injection
 * Follows Clean Architecture - separates concerns into layers
 */
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val logger = KotlinLogging.logger {}
    
    // Install Koin for dependency injection (reduces boilerplate significantly)
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    
    // Call logging is handled by Koin's slf4j logger
    
    // Get controller from Koin
    val websiteController by inject<WebsiteController>()
    
    routing {
        // Serve static resources
        staticResources("/static", "static")
        
        // Main route - uses Clean Architecture with Koin
        get("/") {
            logger.info { "Handling request for homepage" }
            
            try {
                val htmlContent = websiteController.loadWebsite()
                call.response.headers.append("Content-Type", "text/html; charset=UTF-8")
                call.respond(htmlContent)
            } catch (e: Exception) {
                logger.error(e) { "Error handling homepage request" }
                call.respondHtml {
                    head {
                        title("Error - Personal Website")
                        link(rel = "stylesheet", href = "/static/css/style.css", type = "text/css")
                        meta(charset = "UTF-8")
                        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    }
                    body {
                        main {
                            div(classes = "page-content") {
                                h1 { +"Error" }
                                p { +"Failed to load website: ${e.message}" }
                            }
                        }
                    }
                }
            }
        }
    }
}