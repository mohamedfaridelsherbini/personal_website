package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Shared header/nav structure for the site.
 */
object HeaderComponent {

    fun appendHomeHeader(builder: StringBuilder, personalInfo: PersonalInfo) {
        builder.appendLine("    <header>")
        builder.appendLine("        <div class=\"header-left\">")
        builder.appendLine("            <img src=\"/static/images/profile-social.jpg\" alt=\"Portrait of ${personalInfo.name}\" class=\"logo-badge\">")
        builder.appendLine("            <h2 class=\"header-title\">${personalInfo.name}</h2>")
        builder.appendLine("        </div>")
        builder.appendLine("        <button class=\"nav-toggle\" type=\"button\" aria-label=\"Toggle navigation\" aria-expanded=\"false\" data-nav-toggle>")
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("            <span class=\"nav-toggle-bar\"></span>")
        builder.appendLine("        </button>")
        builder.appendLine("        <nav class=\"nav-panel\" data-nav-panel>")
        builder.appendLine("            <div class=\"nav-links\">")
        builder.appendLine("                <a class=\"nav-link\" href=\"#about\">About</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"#skills\">Skills</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"#personal-projects\">Projects</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"#contact\">Contact</a>")
        builder.appendLine("            </div>")
        builder.appendLine("            <div class=\"nav-actions\">")
        builder.appendLine("                <a href=\"mailto:${personalInfo.email}\" class=\"contact-button\">Let's Talk</a>")
        builder.appendLine("                <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"resume-button\" target=\"_blank\" download=\"Mohamed_ElSherbini_Resume.pdf\">Resume</a>")
        builder.appendLine("            </div>")
        builder.appendLine("        </nav>")
        builder.appendLine("    </header>")
    }

    fun appendProjectHeader(builder: StringBuilder, personalInfo: PersonalInfo) {
        builder.appendLine("    <header>")
        builder.appendLine("        <div class=\"header-left\">")
        builder.appendLine("            <img src=\"/static/images/profile-social.jpg\" alt=\"Portrait of ${personalInfo.name}\" class=\"logo-badge\">")
        builder.appendLine("            <h2 class=\"header-title\">${personalInfo.name}</h2>")
        builder.appendLine("        </div>")
        builder.appendLine("        <nav class=\"nav-panel is-static\">")
        builder.appendLine("            <div class=\"nav-links\">")
        builder.appendLine("                <a class=\"nav-link\" href=\"/\">Home</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"/#about\">About</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"/#skills\">Skills</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"/#personal-projects\">Projects</a>")
        builder.appendLine("                <a class=\"nav-link\" href=\"/#contact\">Contact</a>")
        builder.appendLine("            </div>")
        builder.appendLine("            <div class=\"nav-actions\">")
        builder.appendLine("                <a href=\"mailto:${personalInfo.email}\" class=\"contact-button\">Let's Talk</a>")
        builder.appendLine("                <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"resume-button\" target=\"_blank\" download=\"Mohamed_ElSherbini_Resume.pdf\">Resume</a>")
        builder.appendLine("            </div>")
        builder.appendLine("        </nav>")
        builder.appendLine("    </header>")
    }
}
