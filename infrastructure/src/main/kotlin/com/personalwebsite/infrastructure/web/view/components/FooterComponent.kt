@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Shared site footer.
 */
object FooterComponent {
    fun append(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
    ) {
        builder.appendLine("    <footer class=\"site-footer\">")
        builder.appendLine(
            "        <p>© 2026 ${personalInfo.name} · Built with Kotlin &amp; Ktor · <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" target=\"_blank\" rel=\"noopener\">Résumé</a></p>",
        )
        builder.appendLine("    </footer>")
    }
}
