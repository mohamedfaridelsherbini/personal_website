@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalProject

/**
 * "More projects": a flat grid of white cards for everything that is not a big project.
 */
object ProjectsSectionComponent {
    private val excludedSlugs = BigProjectsComponent.SLUGS

    fun append(
        builder: StringBuilder,
        projects: List<PersonalProject>,
    ) {
        builder.appendLine(
            "        <section class=\"page-section projects-section\" id=\"personal-projects\" data-scroll-target=\"personal-projects\">",
        )
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <div class=\"ds-heading-row\">")
        builder.appendLine("                    <h2 class=\"ds-h2 ds-h2--xl\">More projects</h2>")
        builder.appendLine("                    <p class=\"ds-muted\">Apps, reusable libraries, and engineering tools.</p>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"more-projects-grid\">")
        projects.filterNot { it.slug in excludedSlugs }.forEach { project ->
            builder.appendLine("                    <a class=\"more-project-card\" href=\"/projects/${project.slug.escapeHtml()}\">")
            builder.appendLine("                        <span class=\"more-project-category\">${project.category.escapeHtml()}</span>")
            builder.appendLine("                        <h3>${project.name.escapeHtml()}</h3>")
            builder.appendLine("                        <p>${project.description.escapeHtml()}</p>")
            builder.appendLine("                        <ul class=\"more-project-tags\">")
            project.techStack.split(" • ").forEach { tech ->
                builder.appendLine("                            <li>${tech.escapeHtml()}</li>")
            }
            builder.appendLine("                        </ul>")
            builder.appendLine("                        <span class=\"more-project-action\">Explore project ↗</span>")
            builder.appendLine("                    </a>")
        }
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
