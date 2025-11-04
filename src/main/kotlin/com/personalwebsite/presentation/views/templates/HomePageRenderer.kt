package com.personalwebsite.presentation.views.templates

import com.personalwebsite.presentation.models.PageModel
import com.personalwebsite.presentation.views.components.AboutSectionComponent
import com.personalwebsite.presentation.views.components.ContactSectionComponent
import com.personalwebsite.presentation.views.components.FeatureSectionComponent
import com.personalwebsite.presentation.views.components.FooterComponent
import com.personalwebsite.presentation.views.components.HeaderComponent
import com.personalwebsite.presentation.views.components.HeroSectionComponent
import com.personalwebsite.presentation.views.components.PageHeadComponent
import com.personalwebsite.presentation.views.components.ProjectsSectionComponent
import com.personalwebsite.presentation.views.components.QuoteSectionComponent
import com.personalwebsite.presentation.views.components.SkillsSectionComponent

/**
 * Renders the home page using reusable components.
 */
class HomePageRenderer(
    private val headComponent: PageHeadComponent = PageHeadComponent()
) {

    fun render(page: PageModel.Home): String {
        val site = page.site
        val personalInfo = site.personalInfo

        return buildString {
            appendLine("<!DOCTYPE html>")
            appendLine("<html>")
            headComponent.appendHomeHead(this, page.metadata, personalInfo)
            appendLine("<body class=\"bg-[#111714] font-['Spline_Sans']\">")
            HeaderComponent.appendHomeHeader(this, personalInfo)
            appendLine("    <main>")
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
