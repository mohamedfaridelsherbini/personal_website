package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.application.website.WebsiteController
import io.ktor.http.ContentType
import io.ktor.http.withCharset
import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing
import java.nio.charset.StandardCharsets

internal val HtmlUtf8: ContentType = ContentType.Text.Html.withCharset(StandardCharsets.UTF_8)

fun Application.registerRoutes(controller: WebsiteController) {
    routing {
        staticResources("/static", "static")
        homeRoutes(controller)
        projectRoutes(controller)
    }
}
