package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Shared site footer.
 */
object FooterComponent {

    fun append(builder: StringBuilder, personalInfo: PersonalInfo) {
        builder.appendLine("    <footer>")
        builder.appendLine("        <p>© 2025 ${personalInfo.name}. Built with Kotlin, Ktor & Clean Architecture.</p>")
        builder.appendLine("    </footer>")
    }
}
