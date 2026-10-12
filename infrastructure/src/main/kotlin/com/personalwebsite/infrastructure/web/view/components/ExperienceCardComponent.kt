@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.WorkExperience

/**
 * Renders experience rows: company, role and period on the left, highlights on the right.
 */
object ExperienceCardComponent {
    fun append(
        builder: StringBuilder,
        experiences: List<WorkExperience>,
    ) {
        experiences.forEach { experience ->
            val currentClass = if (experience.period.contains("Present")) " role--current" else ""
            builder.appendLine("                    <article class=\"role$currentClass\">")
            builder.appendLine("                        <div class=\"role-head\">")
            builder.appendLine("                            <h3 class=\"role-company\">${experience.company.escapeHtml()}</h3>")
            builder.appendLine("                            <p class=\"role-title\">${experience.position.escapeHtml()}</p>")
            builder.appendLine(
                "                            <p class=\"role-meta\">${experience.period.escapeHtml()} · ${experience.location.escapeHtml()}</p>",
            )
            builder.appendLine("                        </div>")
            builder.appendLine("                        <ul class=\"role-highlights\">")
            experience.responsibilities.take(3).forEach { highlight ->
                builder.appendLine("                            <li>${highlight.escapeHtml()}</li>")
            }
            builder.appendLine("                        </ul>")
            builder.appendLine("                    </article>")
        }
    }
}
