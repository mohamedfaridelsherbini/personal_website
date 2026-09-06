@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.PersonalProject

/**
 * Personal projects grid on the home page: a featured spotlight for flagship
 * projects, followed by a curated grid of everything else.
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

        appendCategoryLegend(builder, projects)

        val featured = projects.filter { it.featured }
        val rest = projects.filterNot { it.featured }

        if (featured.isNotEmpty()) {
            appendFeaturedGrid(builder, featured)
        }

        appendAllProjectsGrid(builder, rest)

        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }

    private fun appendCategoryLegend(
        builder: StringBuilder,
        projects: List<PersonalProject>,
    ) {
        val categories = projects.map { it.category }.distinct()
        builder.appendLine("                <ul class=\"category-legend\">")
        categories.forEach { category ->
            val categoryClass = categoryClassFor(category)
            builder.appendLine(
                "                    <li class=\"legend-chip $categoryClass\"><span class=\"legend-chip-dot\"></span>$category</li>",
            )
        }
        builder.appendLine("                </ul>")
    }

    private fun appendFeaturedGrid(
        builder: StringBuilder,
        featured: List<PersonalProject>,
    ) {
        builder.appendLine("                <div class=\"featured-projects-grid\">")
        featured.forEach { project ->
            val categoryClass = categoryClassFor(project.category)
            builder.appendLine(
                "                    <a href=\"/projects/${project.slug}\" class=\"featured-project-card $categoryClass\">",
            )
            builder.appendLine("                        <div class=\"featured-project-header\">")
            builder.appendLine(
                "                            <span class=\"project-category $categoryClass\">Featured &middot; ${project.category}</span>",
            )
            builder.appendLine("                        </div>")
            builder.appendLine("                        <h3 class=\"featured-project-title\">${project.name}</h3>")
            builder.appendLine("                        <p class=\"featured-project-description\">${project.description}</p>")
            if (project.metrics.isNotEmpty()) {
                builder.appendLine("                        <div class=\"featured-project-metrics\">")
                project.metrics.forEach { metric ->
                    builder.appendLine("                            <span class=\"metric-chip\">$metric</span>")
                }
                builder.appendLine("                        </div>")
            }
            builder.appendLine("                        <div class=\"project-tech-stack\">")
            project.techStack.split(" • ").forEach { tech ->
                builder.appendLine("                            <span class=\"tech-tag\">$tech</span>")
            }
            builder.appendLine("                        </div>")
            builder.appendLine("                    </a>")
        }
        builder.appendLine("                </div>")
    }

    private fun appendAllProjectsGrid(
        builder: StringBuilder,
        rest: List<PersonalProject>,
    ) {
        builder.appendLine("                <div class=\"all-projects-heading\">")
        builder.appendLine("                    <h2 class=\"all-projects-title\">All Projects</h2>")
        builder.appendLine("                    <span class=\"all-projects-count\">${rest.size} projects</span>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"all-projects-grid\">")
        rest.forEach { project ->
            val categoryClass = categoryClassFor(project.category)
            builder.appendLine(
                "                    <a href=\"/projects/${project.slug}\" class=\"minimal-project-card $categoryClass\">",
            )
            builder.appendLine(
                "                        <span class=\"project-category $categoryClass\">${project.category}</span>",
            )
            builder.appendLine("                        <div class=\"project-header\">")
            builder.appendLine("                            <h3 class=\"project-title\">${project.name}</h3>")
            builder.appendLine("                        </div>")
            builder.appendLine("                        <p class=\"project-description\">${project.description}</p>")
            builder.appendLine("                        <div class=\"project-tech-stack\">")
            project.techStack.split(" • ").forEach { tech ->
                builder.appendLine("                            <span class=\"tech-tag\">$tech</span>")
            }
            builder.appendLine("                        </div>")
            builder.appendLine("                    </a>")
        }
        builder.appendLine("                </div>")
    }

    private fun categoryClassFor(category: String): String =
        "category-" +
            category
                .lowercase()
                .replace(" / ", "-")
                .replace(" & ", "-")
                .replace(" ", "-")
}
