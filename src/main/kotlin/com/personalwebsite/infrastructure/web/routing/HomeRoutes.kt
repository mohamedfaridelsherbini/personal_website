package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.application.website.WebsiteController
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.homeRoutes(controller: WebsiteController) {
    get("/") {
        val html = controller.loadWebsite()
        call.respondText(html, HtmlUtf8)
    }
}
