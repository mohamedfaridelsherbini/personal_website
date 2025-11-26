@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.WorkExperience

/**
 * Renders experience cards grid.
 */
object ExperienceCardComponent {
    fun append(
        builder: StringBuilder,
        experiences: List<WorkExperience>,
    ) {
        experiences.forEach { experience ->
            builder.appendLine("                    <article class=\"timeline-item\">")
            builder.appendLine("                        <div class=\"timeline-header\">")
            builder.appendLine("                            <h3 class=\"timeline-role\">${experience.position}</h3>")
            builder.appendLine("                            <div class=\"timeline-meta\">")
            builder.appendLine("                                <span class=\"timeline-company\">${experience.company}</span>")
            builder.appendLine("                                <span>•</span>")
            builder.appendLine("                                <span class=\"timeline-period\">${experience.period}</span>")
            builder.appendLine("                                <span>•</span>")
            builder.appendLine("                                <span class=\"timeline-location\">${experience.location}</span>")
            builder.appendLine("                            </div>")
            builder.appendLine("                        </div>")
            builder.appendLine("                        <ul class=\"timeline-highlights\">")
            experience.responsibilities.take(3).forEach { highlight ->
                builder.appendLine("                            <li class=\"timeline-highlight\">$highlight</li>")
            }
            builder.appendLine("                        </ul>")
            builder.appendLine("                    </article>")
        }
    }
}
