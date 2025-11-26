@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.PersonalProject

/**
 * Personal projects grid on the home page.
 */
object ProjectsSectionComponent {
    fun append(
        builder: StringBuilder,
        projects: List<PersonalProject>,
    ) {
        builder.appendLine("        <section class=\"page-section\" id=\"personal-projects\" data-scroll-target=\"personal-projects\">")
        builder.appendLine("            <div class=\"page-content\">")
        builder.appendLine("                <div class=\"section-heading\">")
        builder.appendLine("                    <p class=\"section-kicker\">Lab notes</p>")
        builder.appendLine("                    <h1 class=\"section-title\">Notable Projects & Open Source</h1>")
        builder.appendLine("                </div>")
        builder.appendLine(
            "                <p class=\"section-subtitle\">A blend of flagship launches, automation frameworks, and open source experiments that showcase architecture, AI, and platform integration work.</p>",
        )
        builder.appendLine("                <div class=\"projects-category-grid\">")
        val groupedProjects = projects.groupBy { it.category }
        
        groupedProjects.forEach { (category, items) ->
            val categoryClass = "category-" + category.lowercase()
                .replace(" / ", "-")
                .replace(" & ", "-")
                .replace(" ", "-")

            builder.appendLine("                    <div class=\"project-group $categoryClass\">")
            builder.appendLine("                        <div class=\"project-group-header\">")
            builder.appendLine("                            <h3 class=\"project-group-title\">$category</h3>")
            builder.appendLine("                        </div>")
            builder.appendLine("                        <div class=\"project-cards\">")
            
            items.forEach { project ->
                builder.appendLine(
                    "                            <a href=\"/projects/${project.slug}\" class=\"minimal-project-card $categoryClass\">",
                )
                builder.appendLine("                                <div class=\"project-header\">")
                builder.appendLine("                                    <h3 class=\"project-title\">${project.name}</h3>")
                // Category badge removed as it is now in the group header
                builder.appendLine("                                </div>")
                builder.appendLine("                                <p class=\"project-description\">${project.description}</p>")
                builder.appendLine("                                <div class=\"project-tech-stack\">")
                project.techStack.split(" • ").forEach { tech ->
                    builder.appendLine("                                    <span class=\"tech-tag\">$tech</span>")
                }
                builder.appendLine("                                </div>")
                builder.appendLine("                            </a>")
            }
            builder.appendLine("                        </div>")
            builder.appendLine("                    </div>")
        }
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
