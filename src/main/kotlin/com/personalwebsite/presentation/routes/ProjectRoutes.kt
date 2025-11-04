package com.personalwebsite.presentation.routes

import com.personalwebsite.presentation.controllers.WebsiteController
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

fun Routing.projectRoutes(controller: WebsiteController) {
    get("/projects/{slug}") {
        val slug = call.parameters["slug"]

        if (slug.isNullOrBlank()) {
            call.respondText(controller.renderError("Missing project slug"), HtmlUtf8, HttpStatusCode.BadRequest)
            return@get
        }

        try {
            val html = controller.loadProject(slug)
            call.respondText(html, HtmlUtf8)
        } catch (missing: NoSuchElementException) {
            val errorHtml = controller.renderError("Project not found")
            call.respondText(errorHtml, HtmlUtf8, HttpStatusCode.NotFound)
        } catch (exception: Exception) {
            logger.error(exception) { "Failed to render project detail for $slug" }
            val errorHtml = controller.renderError("Failed to load project: ${exception.message}")
            call.respondText(errorHtml, HtmlUtf8, HttpStatusCode.InternalServerError)
        }
    }
}
