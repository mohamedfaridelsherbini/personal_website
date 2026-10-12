@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.Skill

/**
 * Technical skills, one row per group.
 */
object SkillsSectionComponent {
    fun append(
        builder: StringBuilder,
        skills: List<Skill>,
    ) {
        builder.appendLine("        <section class=\"page-section skills-section\" id=\"skills\" data-scroll-target=\"skills\">")
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <h2 class=\"ds-h2 ds-h2--xl\">Technical skills</h2>")
        builder.appendLine("                <p class=\"ds-muted\">The tools and practices behind my mobile work.</p>")
        builder.appendLine("                <div class=\"skill-rows\">")
        skills.forEach { skill ->
            builder.appendLine("                    <div class=\"skill-row\">")
            builder.appendLine("                        <h3>${skill.category.escapeHtml()}</h3>")
            builder.appendLine("                        <p>${skill.items.joinToString(" · ") { it.escapeHtml() }}</p>")
            builder.appendLine("                    </div>")
        }
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
