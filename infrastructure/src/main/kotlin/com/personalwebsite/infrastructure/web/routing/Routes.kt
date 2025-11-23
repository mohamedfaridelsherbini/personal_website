package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.application.website.WebsiteQueries
import com.personalwebsite.infrastructure.admin.AdminContentService
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import io.ktor.http.ContentType
import io.ktor.http.withCharset
import io.ktor.server.application.Application
import io.ktor.server.http.content.staticFiles
import io.ktor.server.http.content.staticResources
import io.ktor.server.routing.routing
import java.nio.charset.StandardCharsets

internal val HtmlUtf8: ContentType = ContentType.Text.Html.withCharset(StandardCharsets.UTF_8)

fun Application.registerRoutes(
    websiteQueries: WebsiteQueries,
    adminContentService: AdminContentService,
    adminAuthEnabled: Boolean,
    getPersonalProjectsUseCase: GetPersonalProjectsUseCase,
    siteBaseUrl: String,
) {
    routing {
        staticFiles("/static/files", adminContentService.resumeDirectory().toFile())
        staticResources("/static", "static")
        homeRoutes(websiteQueries)
        projectRoutes(websiteQueries)
        sitemapRoutes(
            getPersonalProjectsUseCase = getPersonalProjectsUseCase,
            siteBaseUrl = siteBaseUrl,
        )
        adminRoutes(adminContentService, adminAuthEnabled)
    }
}
