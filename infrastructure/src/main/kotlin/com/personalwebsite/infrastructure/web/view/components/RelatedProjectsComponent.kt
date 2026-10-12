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
        builder.appendLine("            <div class=\"projects-grid\">")
        relatedProjects.forEach { project ->
            builder.appendLine(
                "                <a href=\"/projects/${project.slug}\" class=\"project-card personal-project-card\">",
            )
            builder.appendLine("                    <div class=\"project-content\">")
            builder.appendLine("                        <div class=\"project-header\">")
            builder.appendLine("                            <h3 class=\"project-title\">${project.name}</h3>")
            builder.appendLine("                            <span class=\"project-category\">${project.category}</span>")
            builder.appendLine("                        </div>")
            builder.appendLine("                        <p class=\"project-description\">${project.summary}</p>")
            builder.appendLine("                        <div class=\"project-tech-stack\">")
            project.techStack.split(" • ").forEach { tech ->
                builder.appendLine("                            <span class=\"tech-tag\">$tech</span>")
            }
            builder.appendLine("                        </div>")
            builder.appendLine("                    </div>")
            builder.appendLine("                </a>")
        }
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
