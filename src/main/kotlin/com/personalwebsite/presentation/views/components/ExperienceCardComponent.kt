package com.personalwebsite.presentation.views.components

import com.personalwebsite.domain.entities.WorkExperience

/**
 * Renders experience cards grid.
 */
object ExperienceCardComponent {

    fun append(builder: StringBuilder, experiences: List<WorkExperience>) {
        experiences.forEach { experience ->
            builder.appendLine("                    <article class=\"experience-card cluster-card\">")
            builder.appendLine("                        <div class=\"experience-card__header\">")
            builder.appendLine("                            <div>")
            builder.appendLine("                                <p class=\"experience-position\">${experience.position}</p>")
            builder.appendLine("                                <p class=\"experience-company\">${experience.company} • ${experience.period}</p>")
            builder.appendLine("                            </div>")
            builder.appendLine("                            <span class=\"experience-location\">${experience.location}</span>")
            builder.appendLine("                        </div>")
            builder.appendLine("                        <ul class=\"experience-highlights\">")
            experience.responsibilities.take(3).forEach { highlight ->
                builder.appendLine("                            <li class=\"experience-highlight\">$highlight</li>")
            }
            builder.appendLine("                        </ul>")
            builder.appendLine("                    </article>")
        }
    }
}
