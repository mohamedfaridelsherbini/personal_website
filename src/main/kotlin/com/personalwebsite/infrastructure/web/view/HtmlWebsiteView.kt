package com.personalwebsite.infrastructure.web.view

import com.personalwebsite.application.website.model.PageModel
import com.personalwebsite.infrastructure.web.view.templates.HomePageRenderer
import com.personalwebsite.infrastructure.web.view.templates.ProjectPageRenderer

/**
 * HTML implementation of the website view built from modular renderers.
 */
class HtmlWebsiteView(
    private val homeRenderer: HomePageRenderer = HomePageRenderer(),
    private val projectRenderer: ProjectPageRenderer = ProjectPageRenderer()
) : WebsiteView {

    override fun render(page: PageModel): String = when (page) {
        is PageModel.Home -> homeRenderer.render(page)
        is PageModel.Project -> projectRenderer.render(page)
    }

    override fun renderError(errorMessage: String): String = buildString {
        appendLine("<!DOCTYPE html>")
        appendLine("<html>")
        appendLine("<head>")
        appendLine("    <title>Error - Personal Website</title>")
        appendLine("    <link rel=\"stylesheet\" href=\"/static/css/style.css\" type=\"text/css\">")
        appendLine("    <meta charset=\"UTF-8\">")
        appendLine("    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">")
        appendLine("</head>")
        appendLine("<body>")
        appendLine("    <main>")
        appendLine("        <div class=\"page-content\">")
        appendLine("            <h1>Error</h1>")
        appendLine("            <p>$errorMessage</p>")
        appendLine("        </div>")
        appendLine("    </main>")
        appendLine("</body>")
        appendLine("</html>")
    }
}
