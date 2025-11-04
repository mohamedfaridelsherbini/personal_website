package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.application.website.WebsiteQueries
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

fun Routing.homeRoutes(websiteQueries: WebsiteQueries) {
    get("/") {
        val html = websiteQueries.renderHome()
        call.respondText(html, HtmlUtf8)
    }
}
