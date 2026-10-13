@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalProject

/**
 * "More projects": a grid of compact project cards.
 */
object ProjectsSectionComponent {
    fun append(
        builder: StringBuilder,
        projects: List<PersonalProject>,
    ) {
        val shown = projects.filterNot { it.featured }
        builder.appendLine(
            "        <section class=\"page-section projects-section\" id=\"personal-projects\" data-scroll-target=\"personal-projects\">",
        )
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <div class=\"ds-heading-row\">")
        builder.appendLine("                    <h2 class=\"ds-h2 ds-h2--xl\">More projects</h2>")
        builder.appendLine("                    <p class=\"ds-muted\">Apps, reusable libraries, and engineering tools.</p>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"more-projects-grid\">")
        shown.forEach { ProjectCardComponent.append(builder, it) }
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
