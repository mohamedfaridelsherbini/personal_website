@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components
import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.entities.WorkExperience

/**
 * About + experience section on the home page.
 */
object AboutSectionComponent {
    fun append(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
        experience: List<WorkExperience>,
    ) {
        builder.appendLine("        <section class=\"page-section\" id=\"about\" data-scroll-target=\"about\">")
        builder.appendLine("            <div class=\"page-content\">")
        builder.appendLine("                <div class=\"about-header\">")
        builder.appendLine("                    <h1 class=\"about-name\">${personalInfo.name}</h1>")
        builder.appendLine("                    <p class=\"about-title\">${personalInfo.title}</p>")
        builder.appendLine("                    <p class=\"about-location\">📍 ${personalInfo.location}</p>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"section-heading\">")
        builder.appendLine("                    <p class=\"section-kicker\">Human layer</p>")
        builder.appendLine("                    <h2 class=\"section-title\">About Me</h2>")
        builder.appendLine("                </div>")
        builder.appendLine("                <p class=\"about-description\">${personalInfo.summary}</p>")
        builder.appendLine(
            "                <p class=\"about-description\">Proficient in Appium, Selenium, Docker, Jenkins, and Spring Boot microservices. I focus on maintainable products that blend technical excellence with delightful user journeys, especially across finance, automation, and highly regulated experiences.</p>",
        )
        builder.appendLine("                <div class=\"about-grid\">")
        builder.appendLine("                    <div class=\"about-card\">")
        builder.appendLine("                        <div class=\"about-card-icon\">🏗️</div>")
        builder.appendLine("                        <h3 class=\"about-card-title\">Composable Architecture</h3>")
        builder.appendLine("                        <p class=\"about-card-desc\">Modular Kotlin bases, KMP adoption, and Jetpack Compose design systems that scale across brands.</p>")
        builder.appendLine("                    </div>")
        builder.appendLine("                    <div class=\"about-card\">")
        builder.appendLine("                        <div class=\"about-card-icon\">📊</div>")
        builder.appendLine("                        <h3 class=\"about-card-title\">Observability & Automation</h3>")
        builder.appendLine("                        <p class=\"about-card-desc\">Selenium/Appium frameworks, Jenkins pipelines, and live dashboards keeping KPIs in sight.</p>")
        builder.appendLine("                    </div>")
        builder.appendLine("                    <div class=\"about-card\">")
        builder.appendLine("                        <div class=\"about-card-icon\">🛡️</div>")
        builder.appendLine("                        <h3 class=\"about-card-title\">Reliability First</h3>")
        builder.appendLine("                        <p class=\"about-card-desc\">Performance and security-first mobile experiences for millions of users in finance and regulated sectors.</p>")
        builder.appendLine("                    </div>")
        builder.appendLine("                </div>")

        builder.appendLine("                <div class=\"section-heading\">")
        builder.appendLine("                    <p class=\"section-kicker\">Career signal</p>")
        builder.appendLine("                    <h2 class=\"section-title\">Experience</h2>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"timeline\">")
        ExperienceCardComponent.append(builder, experience)
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
