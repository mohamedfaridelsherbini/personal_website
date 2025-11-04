package com.personalwebsite.presentation.views.components

/**
 * Highlights the core practice areas.
 */
object FeatureSectionComponent {

    fun append(builder: StringBuilder) {
        builder.appendLine("        <section class=\"features\">")
        builder.appendLine("            <div class=\"section-heading\">")
        builder.appendLine("                <p class=\"section-kicker\">Core strengths</p>")
        builder.appendLine("                <h2 class=\"section-title\">From blueprint to launch</h2>")
        builder.appendLine("            </div>")
        builder.appendLine("            <p class=\"section-subtitle\">Composable architecture, end-to-end automation, and fintech reliability are the pillars behind every engagement I take on.</p>")
        builder.appendLine("            <div class=\"feature-grid\">")
        builder.appendLine("                <div class=\"feature cluster-card\">")
        builder.appendLine("                    <div class=\"feature-icon\">⚙️</div>")
        builder.appendLine("                    <h3>Composable architecture</h3>")
        builder.appendLine("                    <p>Radically modular Kotlin base layers, sealed contracts, and Compose-first UI kits so every squad ships independently without breaking the core system.</p>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"feature cluster-card\">")
        builder.appendLine("                    <div class=\"feature-icon\">📡</div>")
        builder.appendLine("                    <h3>Observability & automation</h3>")
        builder.appendLine("                    <p>Cross-platform UI automation frameworks (Selenium, Appium) wired into Jenkins, Docker, and analytics stacks so release trains stay green and insights stay live.</p>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"feature cluster-card\">")
        builder.appendLine("                    <div class=\"feature-icon\">🔒</div>")
        builder.appendLine("                    <h3>Reliability at scale</h3>")
        builder.appendLine("                    <p>Reliability, performance, and security-first mobile experiences for millions of users, built into every architecture decision, test, and deployment.</p>")
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
