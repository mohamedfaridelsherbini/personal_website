package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import io.ktor.http.ContentType
import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import java.time.LocalDate

fun Routing.sitemapRoutes(
    getPersonalProjectsUseCase: GetPersonalProjectsUseCase,
    siteBaseUrl: String,
) {
    get("/sitemap.xml") {
        val projects = getPersonalProjectsUseCase()
        val today = LocalDate.now().toString()

        val xml =
            buildString {
                appendLine("""<?xml version="1.0" encoding="UTF-8"?>""")
                appendLine("""<urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">""")
                appendLine("  <url>")
                appendLine("    <loc>$siteBaseUrl/</loc>")
                appendLine("    <lastmod>$today</lastmod>")
                appendLine("    <changefreq>weekly</changefreq>")
                appendLine("    <priority>1.0</priority>")
                appendLine("  </url>")

                projects.forEach { project ->
                    appendLine("  <url>")
                    appendLine("    <loc>$siteBaseUrl/projects/${project.slug}</loc>")
                    appendLine("    <changefreq>monthly</changefreq>")
                    appendLine("    <priority>0.8</priority>")
                    appendLine("  </url>")
                }
                appendLine("</urlset>")
            }

        call.respondText(xml, ContentType.Application.Xml)
    }

    get("/robots.txt") {
        val robots =
            """
            User-agent: *
            Allow: /

            Sitemap: $siteBaseUrl/sitemap.xml
            """.trimIndent()

        call.respondText(robots, ContentType.Text.Plain)
    }
}
