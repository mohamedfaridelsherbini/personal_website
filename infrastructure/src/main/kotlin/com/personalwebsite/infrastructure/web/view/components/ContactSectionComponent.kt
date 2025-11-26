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
        builder.appendLine("                <div class=\"contact-grid\">")
        builder.appendLine("                    <a href=\"mailto:${personalInfo.email}\" class=\"contact-card\">")
        builder.appendLine("                        <div class=\"contact-header\">")
        builder.appendLine("                            <div class=\"contact-icon\">")
        builder.appendLine("                                <svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><rect x=\"2\" y=\"4\" width=\"20\" height=\"16\" rx=\"2\"/><path d=\"m22 7-8.97 5.7a1.94 1.94 0 0 1-2.06 0L2 7\"/></svg>")
        builder.appendLine("                            </div>")
        builder.appendLine("                            <span class=\"contact-title\">Email</span>")
        builder.appendLine("                        </div>")
        builder.appendLine("                        <p class=\"contact-description\">Fastest path for briefs, contracts, and support requests.</p>")
        builder.appendLine("                        <span class=\"contact-action\">Send Message</span>")
        builder.appendLine("                    </a>")
        builder.appendLine("                    ")
        builder.appendLine("                    <a href=\"${personalInfo.linkedin}\" target=\"_blank\" rel=\"noopener\" class=\"contact-card\">")
        builder.appendLine("                        <div class=\"contact-header\">")
        builder.appendLine("                            <div class=\"contact-icon\">")
        builder.appendLine("                                <svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M16 8a6 6 0 0 1 6 6v7h-4v-7a2 2 0 0 0-2-2 2 2 0 0 0-2 2v7h-4v-7a6 6 0 0 1 6-6z\"/><rect x=\"2\" y=\"9\" width=\"4\" height=\"12\"/><circle cx=\"4\" cy=\"4\" r=\"2\"/></svg>")
        builder.appendLine("                            </div>")
        builder.appendLine("                            <span class=\"contact-title\">LinkedIn</span>")
        builder.appendLine("                        </div>")
        builder.appendLine("                        <p class=\"contact-description\">Follow product drops, speaking gigs, and fintech notes.</p>")
        builder.appendLine("                        <span class=\"contact-action\">Connect</span>")
        builder.appendLine("                    </a>")
        builder.appendLine("                    ")
        builder.appendLine("                    <a href=\"${personalInfo.github}\" target=\"_blank\" rel=\"noopener\" class=\"contact-card\">")
        builder.appendLine("                        <div class=\"contact-header\">")
        builder.appendLine("                            <div class=\"contact-icon\">")
        builder.appendLine("                                <svg width=\"20\" height=\"20\" viewBox=\"0 0 24 24\" fill=\"none\" stroke=\"currentColor\" stroke-width=\"2\" stroke-linecap=\"round\" stroke-linejoin=\"round\"><path d=\"M9 19c-5 1.5-5-2.5-7-3m14 6v-3.87a3.37 3.37 0 0 0-.94-2.61c3.14-.35 6.44-1.54 6.44-7A5.44 5.44 0 0 0 20 4.77 5.07 5.07 0 0 0 19.91 1S18.73.65 16 2.48a13.38 13.38 0 0 0-7 0C6.27.65 5.09 1 5.09 1A5.07 5.07 0 0 0 5 4.77a5.44 5.44 0 0 0-1.5 3.78c0 5.42 3.3 6.61 6.44 7A3.37 3.37 0 0 0 9 18.13V22\"/></svg>")
        builder.appendLine("                            </div>")
        builder.appendLine("                            <span class=\"contact-title\">GitHub</span>")
        builder.appendLine("                        </div>")
        builder.appendLine("                        <p class=\"contact-description\">Inspect OSS libraries, experiments, and CI templates.</p>")
        builder.appendLine("                        <span class=\"contact-action\">View Profile</span>")
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
