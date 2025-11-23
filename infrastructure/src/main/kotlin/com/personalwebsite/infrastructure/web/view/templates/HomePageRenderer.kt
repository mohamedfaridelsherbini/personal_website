package com.personalwebsite.infrastructure.web.view.templates

import com.personalwebsite.application.website.model.PageModel
import com.personalwebsite.infrastructure.web.view.components.AboutSectionComponent
import com.personalwebsite.infrastructure.web.view.components.ContactSectionComponent
import com.personalwebsite.infrastructure.web.view.components.FeatureSectionComponent
import com.personalwebsite.infrastructure.web.view.components.FooterComponent
import com.personalwebsite.infrastructure.web.view.components.HeaderComponent
import com.personalwebsite.infrastructure.web.view.components.HeroSectionComponent
import com.personalwebsite.infrastructure.web.view.components.PageHeadComponent
import com.personalwebsite.infrastructure.web.view.components.ProjectsSectionComponent
import com.personalwebsite.infrastructure.web.view.components.QuoteSectionComponent
import com.personalwebsite.infrastructure.web.view.components.SkillsSectionComponent

/**
 * Renders the home page using reusable components.
 */
class HomePageRenderer(
    private val headComponent: PageHeadComponent = PageHeadComponent(),
) {
    fun render(page: PageModel.Home): String {
        val site = page.site
        val personalInfo = site.personalInfo

        return buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html lang=\"en\">")
            headComponent.appendHomeHead(this, page.metadata, personalInfo)
            appendLine("<body class=\"bg-[#111714] font-['Spline_Sans']\">")
            appendLine("    <a class=\"skip-link\" href=\"#main\">Skip to content</a>")
            HeaderComponent.appendHomeHeader(this, personalInfo)
            appendLine("    <main id=\"main\">")
            HeroSectionComponent.append(this)
            FeatureSectionComponent.append(this)
            AboutSectionComponent.append(this, personalInfo, site.workExperience)
            SkillsSectionComponent.append(this, site.skills)
            ProjectsSectionComponent.append(this, site.personalProjects)
            ContactSectionComponent.append(this, personalInfo, site.languages)
            QuoteSectionComponent.append(this, personalInfo)
            appendLine("    </main>")
            FooterComponent.append(this, personalInfo)
            appendLine("</body>")
            appendLine("</html>")
        }
    }
}
