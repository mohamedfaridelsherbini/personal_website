package com.personalwebsite

import com.personalwebsite.di.appModule
import com.personalwebsite.presentation.controllers.WebsiteController
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import io.ktor.server.html.respondHtml
import io.ktor.server.http.content.staticResources
import io.ktor.server.response.respondText
import io.ktor.server.application.call
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.server.response.respond
import kotlinx.html.body
import kotlinx.html.div
import kotlinx.html.h1
import kotlinx.html.head
import kotlinx.html.link
import kotlinx.html.main
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.title
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.ktor.ext.inject
import mu.KotlinLogging

/**
 * Main application entry point
 * Started with a simple setup, then added Koin for DI to reduce boilerplate
 */
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val logger = KotlinLogging.logger {}
    
    // Install Koin for dependency injection (learned this from a tutorial)
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    
    // Get controller from Koin - much cleaner than manual DI
    val websiteController by inject<WebsiteController>()
    
    routing {
        // Serve static resources
        staticResources("/static", "static")
        
        // Main route - keeping it simple for now
        get("/") {
            logger.info { "Someone's visiting the homepage" }

            try {
                val htmlContent = websiteController.loadWebsite()
                call.response.headers.append("Content-Type", "text/html; charset=UTF-8")
                call.respondText(htmlContent)
            } catch (e: Exception) {
                logger.error(e) { "Oops, something went wrong with the homepage" }
                call.respondHtml {
                    head {
                        title("Error - Personal Website")
                        link(rel = "stylesheet", href = "/static/css/style.css", type = "text/css")
                        meta(charset = "UTF-8")
                        meta(name = "viewport", content = "width=device-width, initial-scale=1.0")
                    }
                    body {
                        div(classes = "page-content") {
                            h1 { +"Error" }
                            p { +"Failed to load website: ${e.message}" }
                        }
                    }
                }
            }
        }

        get("/projects/{slug}") {
            val slug = call.parameters["slug"]

            if (slug.isNullOrBlank()) {
                call.respond(HttpStatusCode.BadRequest, "Missing project slug")
                return@get
            }

            try {
                val htmlContent = websiteController.loadProject(slug)
                call.respondText(htmlContent, ContentType.Text.Html)
            } catch (e: NoSuchElementException) {
                val errorHtml = websiteController.renderError("Project not found")
                call.respondText(errorHtml, ContentType.Text.Html, HttpStatusCode.NotFound)
            } catch (e: Exception) {
                logger.error(e) { "Failed to render project detail for $slug" }
                val errorHtml = websiteController.renderError("Failed to load project: ${e.message}")
                call.respondText(errorHtml, ContentType.Text.Html, HttpStatusCode.InternalServerError)
            }
        }
    }
}
