@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Shared inspirational quote footer.
 */
object QuoteSectionComponent {
    fun append(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
    ) {
        builder.appendLine("        <section class=\"quote-section\">")
        builder.appendLine(
            "            <p class=\"quote-text\">\"Code for clarity, automate for consistency, and build for longevity.\"</p>",
        )
        builder.appendLine("            <span class=\"quote-author\">— ${personalInfo.name}</span>")
        builder.appendLine("        </section>")
    }
}
