package com.personalwebsite.presentation.views.templates

import com.personalwebsite.presentation.models.PageModel
import com.personalwebsite.presentation.views.components.FooterComponent
import com.personalwebsite.presentation.views.components.HeaderComponent
import com.personalwebsite.presentation.views.components.PageHeadComponent
import com.personalwebsite.presentation.views.components.ProjectHeroComponent
import com.personalwebsite.presentation.views.components.ProjectHighlightsComponent
import com.personalwebsite.presentation.views.components.QuoteSectionComponent
import com.personalwebsite.presentation.views.components.RelatedProjectsComponent

/**
 * Project detail renderer.
 */
class ProjectPageRenderer(
    private val headComponent: PageHeadComponent = PageHeadComponent()
) {

    fun render(page: PageModel.Project): String {
        val personalInfo = page.site.personalInfo
        val relatedProjects = page.site.personalProjects.filter { it.slug != page.project.slug }

        return buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html>")
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
