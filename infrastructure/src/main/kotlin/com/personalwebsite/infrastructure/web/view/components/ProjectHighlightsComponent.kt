package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalProject

/**
 * Highlights list for a project detail page.
 */
object ProjectHighlightsComponent {

    fun append(builder: StringBuilder, project: PersonalProject) {
        if (project.highlights.isEmpty()) return
        builder.appendLine("        <section class=\"project-section cluster-card\">")
        builder.appendLine("            <h2>Highlights</h2>")
        builder.appendLine("            <ul class=\"project-highlights\">")
        project.highlights.forEach { highlight ->
            builder.appendLine("                <li>$highlight</li>")
        }
        builder.appendLine("            </ul>")
        builder.appendLine("        </section>")
    }
}
