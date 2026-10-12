@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Contact companion: a small docked avatar that opens an Email / LinkedIn / GitHub menu.
 * On the home page it also carries a welcome layer that the script shows once per tab session.
 * It is a contact menu only: no chat, microphone or online status.
 */
object CompanionComponent {
    fun append(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
        withWelcome: Boolean,
    ) {
        builder.appendLine("    <div class=\"companion\" data-companion data-welcome=\"$withWelcome\">")
        if (withWelcome) {
            builder.appendLine("        <div class=\"companion-welcome\" data-companion-welcome hidden aria-live=\"polite\">")
            builder.appendLine("            <div class=\"companion-welcome-inner\">")
            builder.appendLine(
                "                <span class=\"companion-avatar companion-avatar--large\" data-expression=\"greeting\" data-welcome-avatar aria-hidden=\"true\"></span>",
            )
            builder.appendLine("                <h2 class=\"companion-welcome-title\">Hi, I’m Mohamed.</h2>")
            builder.appendLine("                <p class=\"companion-welcome-role\">${personalInfo.title.escapeHtml()}</p>")
            builder.appendLine("                <p class=\"companion-welcome-copy\">Welcome to my portfolio.</p>")
            builder.appendLine("                <a class=\"ds-button ds-button--primary\" href=\"#big-projects\">Explore my work</a>")
            builder.appendLine("            </div>")
            builder.appendLine("        </div>")
        }
        builder.appendLine("        <div class=\"companion-popover\" data-companion-popover id=\"companion-menu\" hidden>")
        builder.appendLine("            <p class=\"companion-popover-title\">Open contact options</p>")
        builder.appendLine("            <a href=\"mailto:${personalInfo.email.escapeHtml()}\">Email</a>")
        builder.appendLine("            <a href=\"${personalInfo.linkedin.escapeHtml()}\" target=\"_blank\" rel=\"noopener\">LinkedIn</a>")
        builder.appendLine("            <a href=\"${personalInfo.github.escapeHtml()}\" target=\"_blank\" rel=\"noopener\">GitHub</a>")
        builder.appendLine("            <p class=\"companion-popover-note\">No chat, microphone or online status.</p>")
        builder.appendLine("        </div>")
        builder.appendLine(
            "        <button class=\"companion-button\" type=\"button\" aria-label=\"Open contact options\" aria-expanded=\"false\" aria-controls=\"companion-menu\" data-companion-button>",
        )
        builder.appendLine(
            "            <span class=\"companion-avatar\" data-expression=\"neutral\" data-companion-avatar aria-hidden=\"true\"></span>",
        )
        builder.appendLine("        </button>")
        builder.appendLine("    </div>")
    }
}
