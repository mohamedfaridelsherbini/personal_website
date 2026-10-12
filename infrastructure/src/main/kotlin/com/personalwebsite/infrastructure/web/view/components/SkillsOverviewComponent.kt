@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

/**
 * "What I build with" band shown directly under the hero.
 */
object SkillsOverviewComponent {
    private val columns =
        listOf(
            "Mobile" to listOf("Android · Jetpack Compose", "MVVM · MVI · Clean architecture", "Kotlin Multiplatform · SwiftUI"),
            "Test automation" to listOf("Appium & Selenium (Android/iOS)", "Espresso · JUnit · Mockito", "Automation framework design"),
            "Backend" to listOf("Spring Boot microservices", "REST APIs · PostgreSQL", "SAP integration"),
            "Delivery" to listOf("Docker · Kubernetes", "Jenkins · GitHub Actions", "Code review · Agile"),
        )

    fun append(builder: StringBuilder) {
        builder.appendLine("        <section class=\"skills-overview\" aria-labelledby=\"skills-overview-title\">")
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <h2 class=\"ds-h2\" id=\"skills-overview-title\">What I build with</h2>")
        builder.appendLine("                <div class=\"skills-overview-grid\">")
        columns.forEach { (title, items) ->
            builder.appendLine("                    <div class=\"skills-overview-col\">")
            builder.appendLine("                        <h3>${title.escapeHtml()}</h3>")
            builder.appendLine("                        <ul>")
            items.forEach { builder.appendLine("                            <li>${it.escapeHtml()}</li>") }
            builder.appendLine("                        </ul>")
            builder.appendLine("                    </div>")
        }
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
