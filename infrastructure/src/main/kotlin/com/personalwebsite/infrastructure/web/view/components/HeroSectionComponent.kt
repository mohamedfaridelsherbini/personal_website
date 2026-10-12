@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Skills-led home page hero.
 */
object HeroSectionComponent {
    private val skillChips =
        listOf("Kotlin", "Jetpack Compose", "Kotlin Multiplatform", "MVVM · MVI", "Appium · Selenium", "Spring Boot", "Docker · Jenkins")

    fun append(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
    ) {
        builder.appendLine("        <section class=\"hero hero-intro\" id=\"home\" data-scroll-target=\"home\">")
        builder.appendLine("            <div class=\"hero-intro-inner\">")
        builder.appendLine(
            "                <p class=\"hero-eyebrow\">${personalInfo.title.escapeHtml()} · Augsburg, Germany · 8+ years</p>",
        )
        builder.appendLine(
            "                <h1 class=\"hero-headline\">Android.<br>Kotlin Multiplatform.<br>Automation. Built to last.</h1>",
        )
        builder.appendLine(
            "                <p class=\"hero-intro-lead\">I design and ship reliable mobile products and the architecture, test automation and delivery pipelines behind them — across Europe and MENA.</p>",
        )
        builder.appendLine("                <ul class=\"hero-skill-chips\" aria-label=\"Core skills\">")
        skillChips.forEach { builder.appendLine("                    <li>${it.escapeHtml()}</li>") }
        builder.appendLine("                </ul>")
        builder.appendLine("                <div class=\"hero-intro-actions\">")
        builder.appendLine("                    <a href=\"#big-projects\" class=\"ds-button ds-button--primary\">View big projects</a>")
        builder.appendLine(
            "                    <a href=\"${personalInfo.github.escapeHtml()}\" class=\"ds-button ds-button--outline\" target=\"_blank\" rel=\"noopener\">GitHub ↗</a>",
        )
        builder.appendLine(
            "                    <a href=\"${personalInfo.linkedin.escapeHtml()}\" class=\"ds-button ds-button--outline\" target=\"_blank\" rel=\"noopener\">LinkedIn ↗</a>",
        )
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
