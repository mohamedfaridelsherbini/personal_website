package com.personalwebsite.infrastructure.web.view.templates

import com.personalwebsite.application.website.model.PageModel
import com.personalwebsite.infrastructure.web.view.components.AboutSectionComponent
import com.personalwebsite.infrastructure.web.view.components.BigProjectsComponent
import com.personalwebsite.infrastructure.web.view.components.CompanionComponent
import com.personalwebsite.infrastructure.web.view.components.ContactSectionComponent
import com.personalwebsite.infrastructure.web.view.components.EducationSectionComponent
import com.personalwebsite.infrastructure.web.view.components.FooterComponent
import com.personalwebsite.infrastructure.web.view.components.HeaderComponent
import com.personalwebsite.infrastructure.web.view.components.HeroSectionComponent
import com.personalwebsite.infrastructure.web.view.components.PageHeadComponent
import com.personalwebsite.infrastructure.web.view.components.ProjectsSectionComponent
import com.personalwebsite.infrastructure.web.view.components.QuoteSectionComponent
import com.personalwebsite.infrastructure.web.view.components.SkillsOverviewComponent
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
            appendLine("<body>")
            appendLine("    <a class=\"skip-link\" href=\"#main\">Skip to content</a>")
            HeaderComponent.appendHomeHeader(this, personalInfo)
            appendLine("    <main id=\"main\">")
            HeroSectionComponent.append(this, personalInfo)
            SkillsOverviewComponent.append(this)
            BigProjectsComponent.append(this)
            ProjectsSectionComponent.append(this, site.personalProjects)
            AboutSectionComponent.append(this, personalInfo, site.workExperience)
            SkillsSectionComponent.append(this, site.skills)
            EducationSectionComponent.append(this, site.education, site.languages)
            ContactSectionComponent.append(this, personalInfo)
            QuoteSectionComponent.append(this, personalInfo)
            appendLine("    </main>")
            FooterComponent.append(this, personalInfo)
            CompanionComponent.append(this, personalInfo, withWelcome = true)
            appendLine("</body>")
            appendLine("</html>")
        }
    }
}
