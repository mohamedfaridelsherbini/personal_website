@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalProject

/**
 * Shows related projects below a project detail view.
 */
object RelatedProjectsComponent {
    fun append(
        builder: StringBuilder,
        relatedProjects: List<PersonalProject>,
    ) {
        if (relatedProjects.isEmpty()) return
        builder.appendLine("        <section class=\"project-section\">")
        builder.appendLine("            <h2 class=\"ds-h2 ds-h2--xl\">Other projects</h2>")
        builder.appendLine("            <div class=\"more-projects-grid related-projects-grid\">")
        relatedProjects.forEach { ProjectCardComponent.append(builder, it, "                ") }
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
