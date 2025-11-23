package com.personalwebsite.infrastructure.web.view.templates

import com.personalwebsite.application.website.model.PageModel
import com.personalwebsite.infrastructure.web.view.components.FooterComponent
import com.personalwebsite.infrastructure.web.view.components.HeaderComponent
import com.personalwebsite.infrastructure.web.view.components.PageHeadComponent
import com.personalwebsite.infrastructure.web.view.components.ProjectHeroComponent
import com.personalwebsite.infrastructure.web.view.components.ProjectHighlightsComponent
import com.personalwebsite.infrastructure.web.view.components.QuoteSectionComponent
import com.personalwebsite.infrastructure.web.view.components.RelatedProjectsComponent

/**
 * Project detail renderer.
 */
class ProjectPageRenderer(
    private val headComponent: PageHeadComponent = PageHeadComponent(),
) {
    fun render(page: PageModel.Project): String {
        val personalInfo = page.site.personalInfo
        val relatedProjects = page.site.personalProjects.filter { it.slug != page.project.slug }

        return buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html lang=\"en\">")
            headComponent.appendProjectHead(this, page.metadata, personalInfo)
            appendLine("<body class=\"bg-[#111714] font-['Spline_Sans']\">")
            HeaderComponent.appendProjectHeader(this, personalInfo)
            appendLine("    <main class=\"project-detail\">")
            ProjectHeroComponent.append(this, page.project)
            ProjectHighlightsComponent.append(this, page.project)
            RelatedProjectsComponent.append(this, relatedProjects)
            QuoteSectionComponent.append(this, personalInfo)
            appendLine("    </main>")
            FooterComponent.append(this, personalInfo)
            appendLine("</body>")
            appendLine("</html>")
        }
    }
}
