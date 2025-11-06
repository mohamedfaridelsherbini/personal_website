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
        builder.appendLine("                <ul class=\"about-points\">")
        builder.appendLine(
            "                    <li>Composable architecture — modular Kotlin bases, KMP adoption, and Jetpack Compose design systems that scale across brands.</li>",
        )
        builder.appendLine(
            "                    <li>Observability & automation — Selenium/Appium frameworks, Jenkins pipelines, and live dashboards keeping KPIs in sight.</li>",
        )
        builder.appendLine(
            "                    <li>Reliability, performance, and security-first mobile experiences for millions of users.</li>",
        )
        builder.appendLine("                </ul>")

        builder.appendLine("                <div class=\"section-heading\">")
        builder.appendLine("                    <p class=\"section-kicker\">Career signal</p>")
        builder.appendLine("                    <h2 class=\"section-title\">Experience</h2>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"experience-grid\">")
        ExperienceCardComponent.append(builder, experience)
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
