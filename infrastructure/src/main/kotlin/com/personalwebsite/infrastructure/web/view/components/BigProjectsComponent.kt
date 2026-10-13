@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalProject

/**
 * Big projects area: every project placed as "Master" in the admin (`featured`) gets a large card,
 * followed by the Aldaem "coming soon" announcement. No details are invented for Aldaem.
 */
object BigProjectsComponent {
    fun append(
        builder: StringBuilder,
        projects: List<PersonalProject>,
    ) {
        builder.appendLine(
            "        <section class=\"page-section big-projects\" id=\"big-projects\" data-scroll-target=\"big-projects\">",
        )
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <div class=\"ds-heading-row\">")
        builder.appendLine("                    <h2 class=\"ds-h2 ds-h2--xl\">Big projects</h2>")
        builder.appendLine("                    <p class=\"ds-muted\">Products I own end to end</p>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"big-project-grid\">")
        projects.filter { it.featured }.forEachIndexed { index, project ->
            val tone = if (index == 0) "big-project--featured" else "big-project--light"
            builder.appendLine("                    <a class=\"big-project $tone\" href=\"/projects/${project.slug.escapeHtml()}\">")
            builder.appendLine("                        <div>")
            builder.appendLine(
                "                            <p class=\"big-project-status\">Featured · ${project.category.escapeHtml()}</p>",
            )
            builder.appendLine("                            <h3>${project.name.escapeHtml()}</h3>")
            builder.appendLine("                            <p class=\"big-project-desc\">${project.description.escapeHtml()}</p>")
            builder.appendLine("                        </div>")
            builder.appendLine("                        <span class=\"big-project-action\">Explore ${project.name.escapeHtml()} ↗</span>")
            builder.appendLine("                    </a>")
        }
        builder.appendLine("                    <article class=\"big-project big-project--soon\">")
        builder.appendLine("                        <div>")
        builder.appendLine("                            <p class=\"big-project-status\">Coming soon</p>")
        builder.appendLine("                            <h3>Aldaem</h3>")
        builder.appendLine("                            <p class=\"big-project-desc\">Next big project — details to be announced.</p>")
        builder.appendLine("                        </div>")
        builder.appendLine("                    </article>")
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
