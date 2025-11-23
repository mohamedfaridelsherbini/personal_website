package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.application.website.WebsiteQueries
import com.personalwebsite.infrastructure.admin.AdminContentService
import io.ktor.http.ContentType
import io.ktor.http.withCharset
import io.ktor.server.application.Application
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing
import java.nio.charset.StandardCharsets

internal val HtmlUtf8: ContentType = ContentType.Text.Html.withCharset(StandardCharsets.UTF_8)

fun Application.registerRoutes(
    websiteQueries: WebsiteQueries,
    adminContentService: AdminContentService,
    adminAuthEnabled: Boolean,
) {
    routing {
        staticResources("/static", "static")
        homeRoutes(websiteQueries)
        projectRoutes(websiteQueries)
        adminRoutes(adminContentService, adminAuthEnabled)
    }
}
