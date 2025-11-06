@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.Skill

/**
 * Technical skills grid.
 */
object SkillsSectionComponent {
    fun append(
        builder: StringBuilder,
        skills: List<Skill>,
    ) {
        builder.appendLine("        <section class=\"page-section\" id=\"skills\" data-scroll-target=\"skills\">")
        builder.appendLine("            <div class=\"page-content\">")
        builder.appendLine("                <div class=\"section-heading\">")
        builder.appendLine("                    <p class=\"section-kicker\">Capability stack</p>")
        builder.appendLine("                    <h1 class=\"section-title\">Technical Skills</h1>")
        builder.appendLine("                </div>")
        builder.appendLine(
            "                <p class=\"section-subtitle\">Tooling and languages I reach for when blueprinting performant, future-proof Android ecosystems.</p>",
        )
        builder.appendLine("                <div class=\"skills-grid\">")
        skills.forEach { skill ->
            builder.appendLine("                    <div class=\"skill-cluster cluster-card\">")
            builder.appendLine("                        <div class=\"skill-cluster-header\">")
            builder.appendLine("                            <span class=\"skill-cluster-badge\">${skill.category}</span>")
            builder.appendLine("                        </div>")
            builder.appendLine("                        <div class=\"skill-cluster-tags\">")
            skill.items.forEach { item ->
                builder.appendLine("                            <div class=\"skill-tag\">$item</div>")
            }
            builder.appendLine("                        </div>")
            builder.appendLine("                    </div>")
        }
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
