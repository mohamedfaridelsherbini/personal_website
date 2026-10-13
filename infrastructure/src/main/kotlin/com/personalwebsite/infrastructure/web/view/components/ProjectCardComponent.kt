@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalProject

/**
 * Compact white card used for "More projects" on the home page and "Other projects" on project pages.
 */
object ProjectCardComponent {
    fun append(
        builder: StringBuilder,
        project: PersonalProject,
        indent: String = "                    ",
    ) {
        val meta = listOfNotNull(project.role, project.timeline).filter { it.isNotBlank() }.joinToString(" · ")
        builder.appendLine(
            "$indent<a class=\"more-project-card\" href=\"/projects/${project.slug.escapeHtml()}\" data-category=\"${project.category.escapeHtml()}\">",
        )
        builder.appendLine("$indent    <span class=\"more-project-category\">${project.category.escapeHtml()}</span>")
        builder.appendLine("$indent    <h3>${project.name.escapeHtml()}</h3>")
        if (meta.isNotEmpty()) builder.appendLine("$indent    <p class=\"more-project-meta\">${meta.escapeHtml()}</p>")
        builder.appendLine("$indent    <p class=\"more-project-desc\">${project.description.escapeHtml()}</p>")
        builder.appendLine("$indent    <ul class=\"more-project-tags\">")
        project.techStack.split(" • ").filter { it.isNotBlank() }.forEach { tech ->
            builder.appendLine("$indent        <li>${tech.escapeHtml()}</li>")
        }
        builder.appendLine("$indent    </ul>")
        builder.appendLine(
            "$indent    <span class=\"more-project-action\"><span>Explore project</span><span aria-hidden=\"true\">↗</span></span>",
        )
        builder.appendLine("$indent</a>")
    }
}
