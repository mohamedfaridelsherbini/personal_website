@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

/**
 * Home page hero section.
 */
object HeroSectionComponent {
    fun append(builder: StringBuilder) {
        builder.appendLine("        <section class=\"hero\" id=\"home\" data-scroll-target=\"home\">")
        builder.appendLine("            <div class=\"hero-content\">")
        builder.appendLine("                <div class=\"hero-text cluster-card\">")
        builder.appendLine("                    <div class=\"hero-chip-row\">")
        builder.appendLine("                        <span class=\"hero-chip\">Augsburg</span>")
        builder.appendLine("                        <span class=\"hero-chip\">Kotlin</span>")
        builder.appendLine("                        <span class=\"hero-chip\">Swift</span>")
        builder.appendLine("                        <span class=\"hero-chip\">CI/CD</span>")
        builder.appendLine("                        <span class=\"hero-chip\">KMP</span>")
        builder.appendLine("                    </div>")
        builder.appendLine("                    <h1 class=\"hero-title\">Mohamed ElSherbini</h1>")
        builder.appendLine(
            "                    <p class=\"hero-subtitle\">Senior Android Engineer · Kotlin · KMP · Automation</p>",
        )
        builder.appendLine(
            "                    <p class=\"hero-lead\">Senior Android Engineer with 8+ years building reliable mobile applications and scalable architectures across Europe and MENA. Focused on Android, KMP, automation, and high-quality delivery within Agile, cross-functional teams.</p>",
        )
        builder.appendLine("                    <ul class=\"hero-highlight-list\">")
        builder.appendLine(
            "                        <li>Composable Android & KMP architectures rolled out across multi-brand platforms</li>",
        )
        builder.appendLine("                        <li>Automation frameworks (Selenium, Appium) powering Android/iOS CI/CD</li>")
        builder.appendLine(
            "                        <li>Reliability, performance, and security-first mobile experiences for millions of users.</li>",
        )
        builder.appendLine("                    </ul>")
        builder.appendLine("                    <div class=\"hero-actions hero-cta-row\">")
        builder.appendLine("                        <a href=\"#personal-projects\" class=\"button-accent\">View Projects</a>")
        builder.appendLine("                        <a href=\"#contact\" class=\"secondary-button\">Contact</a>")
        builder.appendLine(
            "                        <a href=\"/static/files/Mohamed_ElSherbini_Resume.pdf\" class=\"ghost-button\" target=\"_blank\" rel=\"noopener\">Résumé</a>",
        )
        builder.appendLine("                    </div>")
        builder.appendLine("                    <div class=\"hero-meta-row\">")
        builder.appendLine("                        <div class=\"hero-meta-pill\">")
        builder.appendLine("                            <span class=\"hero-meta-label\">Availability</span>")
        builder.appendLine("                            <span class=\"hero-meta-value\">Q1 2026</span>")
        builder.appendLine("                        </div>")
        builder.appendLine("                        <div class=\"hero-meta-pill\">")
        builder.appendLine("                            <span class=\"hero-meta-label\">Current mission</span>")
        builder.appendLine("                            <span class=\"hero-meta-value\">CHECK24 Mobile Platform</span>")
        builder.appendLine("                        </div>")
        builder.appendLine("                    </div>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"hero-panels\">")
        builder.appendLine("                    <div class=\"hero-panel cluster-card hero-signal\">")
        builder.appendLine("                        <h3>Systems online</h3>")
        builder.appendLine(
            "                        <p>Composable design systems, real-time observability, and battle-tested release trains keep every build stable in production.</p>",
        )
        builder.appendLine("                        <ul class=\"hero-signal-list\">")
        builder.appendLine("                            <li>Jetpack Compose and KMP rolled out across multi-brand Android platforms</li>")
        builder.appendLine(
            "                            <li>Automation frameworks (Selenium, Appium) powering shared Android/iOS pipelines</li>",
        )
        builder.appendLine(
            "                            <li>Security reviews covering encryption, biometric flows, and fintech compliance</li>",
        )
        builder.appendLine("                        </ul>")
        builder.appendLine("                        <div class=\"hero-tag-cloud\">")
        builder.appendLine("                            <span class=\"hero-tag\">Kotlin</span>")
        builder.appendLine("                            <span class=\"hero-tag\">Compose</span>")
        builder.appendLine("                            <span class=\"hero-tag\">Swift</span>")
        builder.appendLine("                            <span class=\"hero-tag\">KMP</span>")
        builder.appendLine("                            <span class=\"hero-tag\">CI/CD</span>")
        builder.appendLine("                        </div>")
        builder.appendLine("                    </div>")
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
