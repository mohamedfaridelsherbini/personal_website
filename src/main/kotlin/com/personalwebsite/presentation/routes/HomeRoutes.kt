package com.personalwebsite.presentation.routes

import com.personalwebsite.presentation.controllers.WebsiteController
import com.personalwebsite.presentation.routes.HtmlUtf8
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
