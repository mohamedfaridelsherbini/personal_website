@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.Education
import com.personalwebsite.domain.entities.Language

/**
 * Education and spoken languages.
 */
object EducationSectionComponent {
    fun append(
        builder: StringBuilder,
        education: List<Education>,
        languages: List<Language>,
    ) {
        if (education.isEmpty() && languages.isEmpty()) return
        builder.appendLine("        <section class=\"page-section education-section\" id=\"education\" data-scroll-target=\"education\">")
        builder.appendLine("            <div class=\"ds-container education-columns\">")
        builder.appendLine("                <div>")
        builder.appendLine("                    <h2 class=\"ds-h2\">Education</h2>")
        education.forEach { edu ->
            builder.appendLine("                    <p class=\"edu-degree\">${edu.degree.escapeHtml()}</p>")
            builder.appendLine("                    <p class=\"ds-muted\">${edu.institution.escapeHtml()} · ${edu.period.escapeHtml()}</p>")
        }
        builder.appendLine("                </div>")
        builder.appendLine("                <div>")
        builder.appendLine("                    <h2 class=\"ds-h2\">Spoken languages</h2>")
        languages.forEach { language ->
            builder.appendLine(
                "                    <div class=\"language-row\"><span>${language.name.escapeHtml()}</span><span class=\"ds-muted\">${language.level.escapeHtml()}</span></div>",
            )
        }
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
