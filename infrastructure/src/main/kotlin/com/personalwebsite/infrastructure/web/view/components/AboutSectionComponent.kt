@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.entities.WorkExperience

/**
 * About + experience sections on the home page.
 */
object AboutSectionComponent {
    fun append(
        builder: StringBuilder,
        personalInfo: PersonalInfo,
        experience: List<WorkExperience>,
    ) {
        builder.appendLine("        <section class=\"page-section about-section\" id=\"about\" data-scroll-target=\"about\">")
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <h2 class=\"ds-h2 ds-h2--xl\">Engineering with care.</h2>")
        builder.appendLine("                <div class=\"about-columns\">")
        builder.appendLine("                    <p class=\"about-summary\">${personalInfo.summary.escapeHtml()}</p>")
        builder.appendLine(
            "                    <p class=\"ds-muted\">Maintainable products, thoughtful user journeys, and dependable delivery — supported by Appium, Selenium, Docker, Jenkins, and Spring Boot microservices.</p>",
        )
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
        builder.appendLine(
            "        <section class=\"page-section experience-section\" id=\"experience\" data-scroll-target=\"experience\">",
        )
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <h2 class=\"ds-h2 ds-h2--xl\">Experience</h2>")
        builder.appendLine("                <div class=\"role-list\">")
        ExperienceCardComponent.append(builder, experience)
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
