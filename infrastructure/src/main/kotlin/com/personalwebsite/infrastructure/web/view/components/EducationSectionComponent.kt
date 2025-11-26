package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.Education

object EducationSectionComponent {
    fun append(
        builder: StringBuilder,
        education: List<Education>,
    ) {
        if (education.isEmpty()) return

        builder.appendLine("        <section class=\"page-section\" id=\"education\">")
        builder.appendLine("            <div class=\"section-container\">")
        builder.appendLine("                <div class=\"section-header\">")
        builder.appendLine("                    <h2 class=\"section-title\">Education</h2>")
        builder.appendLine("                    <p class=\"section-subtitle\">Academic background and qualifications.</p>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"timeline\">")
        
        education.forEach { edu ->
            builder.appendLine("                    <div class=\"timeline-item\">")
            builder.appendLine("                        <div class=\"timeline-marker\"></div>")
            builder.appendLine("                        <div class=\"timeline-content\">")
            builder.appendLine("                            <div class=\"timeline-header\">")
            builder.appendLine("                                <h3 class=\"timeline-title\">${edu.degree}</h3>")
            builder.appendLine("                                <span class=\"timeline-period\">${edu.period}</span>")
            builder.appendLine("                            </div>")
            builder.appendLine("                            <p class=\"timeline-company\">${edu.institution}</p>")
            builder.appendLine("                        </div>")
            builder.appendLine("                    </div>")
        }
        
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
