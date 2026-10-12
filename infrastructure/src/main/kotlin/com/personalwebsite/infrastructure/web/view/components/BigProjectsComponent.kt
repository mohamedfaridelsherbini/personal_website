@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.view.components

/**
 * Big projects area: AlaBal (featured) and Aldaem (coming soon).
 * Details for both are still to be supplied by the owner, so no features or outcomes are invented.
 */
object BigProjectsComponent {
    fun append(builder: StringBuilder) {
        builder.appendLine(
            "        <section class=\"page-section big-projects\" id=\"big-projects\" data-scroll-target=\"big-projects\">",
        )
        builder.appendLine("            <div class=\"ds-container\">")
        builder.appendLine("                <div class=\"ds-heading-row\">")
        builder.appendLine("                    <h2 class=\"ds-h2 ds-h2--xl\">Big projects</h2>")
        builder.appendLine("                    <p class=\"ds-muted\">Products I own end to end</p>")
        builder.appendLine("                </div>")
        builder.appendLine("                <div class=\"big-project-grid\">")
        builder.appendLine("                    <article class=\"big-project big-project--featured\">")
        builder.appendLine("                        <div>")
        builder.appendLine("                            <p class=\"big-project-status\">Featured · Personal project</p>")
        builder.appendLine("                            <h3>AlaBal</h3>")
        builder.appendLine("                            <p class=\"big-project-desc\">A private place-sharing app for family.</p>")
        builder.appendLine("                        </div>")
        builder.appendLine("                    </article>")
        builder.appendLine("                    <article class=\"big-project big-project--soon\">")
        builder.appendLine("                        <div>")
        builder.appendLine("                            <p class=\"big-project-status\">Coming soon</p>")
        builder.appendLine("                            <h3>Aldaem</h3>")
        builder.appendLine("                            <p class=\"big-project-desc\">Next big project — details to be announced.</p>")
        builder.appendLine("                        </div>")
        builder.appendLine("                    </article>")
        builder.appendLine("                </div>")
        builder.appendLine("            </div>")
        builder.appendLine("        </section>")
    }
}
