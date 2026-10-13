@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalInfo

/**
 * GitHub and LinkedIn link cards.
 */
object ContactSectionComponent {
    fun append(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
    ) {
        builder.appendLine("        <section class=\"page-section contact-section\" id=\"contact\" data-scroll-target=\"contact\">")
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <h2 class=\"ds-h2 ds-h2--xl\">Let’s build something thoughtful.</h2>")
        builder.appendLine("                <div class=\"link-card-grid\">")
        listOf(
            Triple("GitHub", personalInfo.github, "Source code, libraries and automation work"),
            Triple("LinkedIn", personalInfo.linkedin, "Experience, roles and contact"),
        ).forEach { (title, href, desc) ->
            val external = " target=\"_blank\" rel=\"noopener\""
            val label = href.removePrefix("https://").removePrefix("http://")
            builder.appendLine("                    <a class=\"link-card\" href=\"${href.escapeHtml()}\"$external>")
            builder.appendLine("                        <h3>$title ↗</h3>")
            builder.appendLine("                        <p>${desc.escapeHtml()}</p>")
            builder.appendLine("                        <span class=\"link-card-url\">${label.escapeHtml()}</span>")
            builder.appendLine("                    </a>")
        }
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
