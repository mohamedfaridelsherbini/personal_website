package com.personalwebsite.presentation.views.components

import com.personalwebsite.domain.entities.PersonalProject

/**
 * Project detail hero section.
 */
object ProjectHeroComponent {

    fun append(builder: StringBuilder, project: PersonalProject) {
        builder.appendLine("        <section class=\"project-hero cluster-card\">")
        if (!project.timeline.isNullOrBlank() || !project.role.isNullOrBlank()) {
            builder.appendLine("            <div class=\"project-pills\">")
            project.timeline?.let {
                builder.appendLine("                <span class=\"project-pill\">$it</span>")
            }
            project.role?.let {
                builder.appendLine("                <span class=\"project-pill\">$it</span>")
            }
            builder.appendLine("            </div>")
        }
        builder.appendLine("            <h1 class=\"project-title\">${project.name}</h1>")
        builder.appendLine("            <p class=\"project-summary\">${project.summary}</p>")
        builder.appendLine("            <div class=\"project-meta\">")
        builder.appendLine("                <div class=\"project-meta-block\">")
        builder.appendLine("                    <h3>Stack</h3>")
        builder.appendLine("                    <div class=\"project-tags\">")
        project.techStack.split(" • ").forEach { tech ->
            builder.appendLine("                        <span class=\"project-tag\">$tech</span>")
        }
        builder.appendLine("                    </div>")
        builder.appendLine("                </div>")
        if (project.metrics.isNotEmpty()) {
            builder.appendLine("                <div class=\"project-meta-block\">")
            builder.appendLine("                    <h3>Metrics</h3>")
            builder.appendLine("                    <ul class=\"project-metrics\">")
            project.metrics.forEach { metric ->
                builder.appendLine("                        <li>$metric</li>")
            }
            builder.appendLine("                    </ul>")
            builder.appendLine("                </div>")
        }
        if (project.links.isNotEmpty()) {
            builder.appendLine("                <div class=\"project-meta-block\">")
            builder.appendLine("                    <h3>Links</h3>")
            builder.appendLine("                    <div class=\"project-links\">")
            project.links.forEach { link ->
                builder.appendLine("                        <a class=\"project-link\" href=\"${link.url}\" target=\"_blank\" rel=\"noopener\">${link.label}</a>")
            }
            builder.appendLine("                    </div>")
            builder.appendLine("                </div>")
        }
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
