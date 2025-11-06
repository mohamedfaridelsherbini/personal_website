@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.Language
import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Contact and languages section on the home page.
 */
object ContactSectionComponent {
    fun append(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
        languages: List<Language>,
    ) {
        builder.appendLine("        <section class=\"page-section\" id=\"contact\" data-scroll-target=\"contact\">")
        builder.appendLine("            <div class=\"page-content\">")
        builder.appendLine("                <div class=\"section-heading\">")
        builder.appendLine("                    <p class=\"section-kicker\">Comm link</p>")
        builder.appendLine("                    <h1 class=\"section-title\">Get In Touch</h1>")
        builder.appendLine("                </div>")
        builder.appendLine(
            "                <p class=\"section-subtitle\">Tell me about the Android platform you’re scaling, the audit you need, or the release train that keeps wobbling. I’ll get back within 24 hours.</p>",
        )
        builder.appendLine("                <div class=\"contact-info\">")
        builder.appendLine("                    <a href=\"mailto:${personalInfo.email}\" class=\"contact-item\">")
        builder.appendLine("                        <span class=\"contact-label\">Email</span>")
        builder.appendLine("                        <h3>${personalInfo.email}</h3>")
        builder.appendLine("                        <p>Fastest path for briefs, contracts, and support requests.</p>")
        builder.appendLine("                    </a>")
        builder.appendLine(
            "                    <a href=\"${personalInfo.linkedin}\" target=\"_blank\" rel=\"noopener\" class=\"contact-item\">",
        )
        builder.appendLine("                        <span class=\"contact-label\">LinkedIn</span>")
        builder.appendLine("                        <h3>${personalInfo.linkedin.displayText()}</h3>")
        builder.appendLine("                        <p>Follow product drops, speaking gigs, and fintech notes.</p>")
        builder.appendLine("                    </a>")
        builder.appendLine(
            "                    <a href=\"${personalInfo.github}\" target=\"_blank\" rel=\"noopener\" class=\"contact-item\">",
        )
        builder.appendLine("                        <span class=\"contact-label\">GitHub</span>")
        builder.appendLine("                        <h3>${personalInfo.github.displayText()}</h3>")
        builder.appendLine("                        <p>Inspect OSS libraries, experiments, and CI templates.</p>")
        builder.appendLine("                    </a>")
        builder.appendLine("                </div>")

        builder.appendLine("                <div class=\"languages-section\">")
        builder.appendLine("                    <div class=\"section-heading\">")
        builder.appendLine("                        <p class=\"section-kicker\">Signal clarity</p>")
        builder.appendLine("                        <h2 class=\"section-title\">Languages</h2>")
        builder.appendLine("                    </div>")
        builder.appendLine("                    <div class=\"languages-grid\">")
        languages.forEach { language ->
            builder.appendLine("                        <div class=\"language-item\">")
            builder.appendLine("                            <span class=\"language\">${language.name}</span>")
            builder.appendLine("                            <span class=\"level\">${language.level}</span>")
            builder.appendLine("                        </div>")
        }
        builder.appendLine("                    </div>")
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }

    private fun String.displayText(): String = removePrefix("https://").removePrefix("http://")
}
