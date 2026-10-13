@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Shared header/nav structure for the site.
 */
object HeaderComponent {
    fun appendHomeHeader(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
    ) {
        builder.appendLine("    <header>")
        builder.appendLine("        <div class=\"header-left\">")
        builder.appendLine(
            "            <img src=\"/static/images/profile-social.jpg\" alt=\"Portrait of ${personalInfo.name}\" class=\"logo-badge\" loading=\"lazy\" decoding=\"async\">",
        )
        builder.appendLine("            <h2 class=\"header-title\">${personalInfo.name}</h2>")
        builder.appendLine("        </div>")
        builder.appendLine(
            "        <button class=\"nav-toggle\" type=\"button\" aria-label=\"Toggle navigation\" aria-expanded=\"false\" data-nav-toggle>",
        )
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("        </button>")
        builder.appendLine("        <nav class=\"nav-panel\" data-nav-panel>")
        builder.appendLine("            <div class=\"nav-links\">")
        builder.appendLine("                <a class=\"nav-link\" href=\"#big-projects\">Work</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"#about\">About</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"#contact\">Contact</a>")
        builder.appendLine("            </div>")
        builder.appendLine("        </nav>")
        builder.appendLine("    </header>")
    }

    fun appendProjectHeader(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
    ) {
        builder.appendLine("    <header>")
        builder.appendLine("        <div class=\"header-left\">")
        builder.appendLine(
            "            <img src=\"/static/images/profile-social.jpg\" alt=\"Portrait of ${personalInfo.name}\" class=\"logo-badge\" loading=\"lazy\" decoding=\"async\">",
        )
        builder.appendLine("            <h2 class=\"header-title\">${personalInfo.name}</h2>")
        builder.appendLine("        </div>")
        builder.appendLine(
            "        <button class=\"nav-toggle\" type=\"button\" aria-label=\"Toggle navigation\" aria-expanded=\"false\" data-nav-toggle>",
        )
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("        </button>")
        builder.appendLine("        <nav class=\"nav-panel is-static\" data-nav-panel>")
        builder.appendLine("            <div class=\"nav-links\">")
        builder.appendLine("                <a class=\"nav-link\" href=\"/\">Home</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"/#big-projects\">Work</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"/#about\">About</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"/#contact\">Contact</a>")
        builder.appendLine("            </div>")
        builder.appendLine("        </nav>")
        builder.appendLine("    </header>")
    }
}
