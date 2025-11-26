package com.personalwebsite.application.website

import com.personalwebsite.application.website.model.PageMetadata
import com.personalwebsite.application.website.model.PageModel
import com.personalwebsite.application.website.model.WebsiteViewModel
import com.personalwebsite.application.website.ports.RenderCache
import com.personalwebsite.application.website.ports.WebsiteView
import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.usecases.GetEducationUseCase
import com.personalwebsite.domain.usecases.GetLanguagesUseCase
import com.personalwebsite.domain.usecases.GetPersonalInfoUseCase
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import com.personalwebsite.domain.usecases.GetSkillsUseCase
import com.personalwebsite.domain.usecases.GetWorkExperienceUseCase
import mu.KotlinLogging

/**
 * Application service coordinating the website use cases.
 */
class WebsiteService(
    private val getPersonalInfoUseCase: GetPersonalInfoUseCase,
    private val getSkillsUseCase: GetSkillsUseCase,
    private val getWorkExperienceUseCase: GetWorkExperienceUseCase,
    private val getPersonalProjectsUseCase: GetPersonalProjectsUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val getEducationUseCase: GetEducationUseCase,
    private val websiteView: WebsiteView,
    private val renderCache: RenderCache,
) : WebsiteQueries {
    private val logger = KotlinLogging.logger {}
    private val baseUrl = "https://www.mohamedfaridelsherbini.com"

    override suspend fun renderHome(): String {
        val cacheKey = "GET:/"
        renderCache.peek(cacheKey)?.let {
            logger.debug { "Serving cached home page" }
            return it
        }

        logger.info { "Cache miss for home page, generating HTML" }

        return try {
            renderCache.getOrPut(cacheKey) {
                val personalInfo = getPersonalInfoUseCase()
                val skills = getSkillsUseCase()
                val workExperience = getWorkExperienceUseCase()
                val personalProjects = getPersonalProjectsUseCase()
                val languages = getLanguagesUseCase()
                val education = getEducationUseCase()

                logger.debug {
                    "Loaded ${skills.size} skill categories, ${workExperience.size} work experiences, ${personalProjects.size} projects"
                }

                val viewModel =
                    WebsiteViewModel(
                        personalInfo = personalInfo,
                        skills = skills,
                        workExperience = workExperience,
                        personalProjects = personalProjects,
                        languages = languages,
                        education = education,
                    )

                val metadata = buildHomeMetadata(personalInfo)
                websiteView.render(PageModel.Home(metadata = metadata, site = viewModel))
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to load website data - ${e.message}" }
            websiteView.renderError("Failed to load website data: ${e.message}")
        }
    }

    override suspend fun renderProject(slug: String): String {
        val cacheKey = "GET:/projects/$slug"
        renderCache.peek(cacheKey)?.let {
            logger.debug { "Serving cached project page for $slug" }
            return it
        }

        logger.info { "Cache miss for project detail $slug, generating HTML" }

        val personalInfo = getPersonalInfoUseCase()

        return try {
            renderCache.getOrPut(cacheKey) {
                val skills = getSkillsUseCase()
                val workExperience = getWorkExperienceUseCase()
                val personalProjects = getPersonalProjectsUseCase()
                val languages = getLanguagesUseCase()
                val education = getEducationUseCase()

                val project =
                    personalProjects.find { it.slug == slug }
                        ?: throw NoSuchElementException("Project not found: $slug")

                val viewModel =
                    WebsiteViewModel(
                        personalInfo = personalInfo,
                        skills = skills,
                        workExperience = workExperience,
                        personalProjects = personalProjects,
                        languages = languages,
                        education = education,
                    )

                val metadata = buildProjectMetadata(personalInfo.name, project)
                websiteView.render(
                    PageModel.Project(
                        project = project,
                        metadata = metadata,
                        site = viewModel,
                    ),
                )
            }
        } catch (e: NoSuchElementException) {
            renderCache.invalidate(cacheKey)
            throw e
        }
    }

    override fun renderError(message: String) = websiteView.renderError(message)

    private fun buildHomeMetadata(personalInfo: PersonalInfo): PageMetadata {
        val metaDescription =
            "Senior Android engineer crafting Kotlin and Jetpack Compose experiences for fintech and product teams across Europe. " +
                "Currently at Check24 in Munich, open to remote consulting and leadership opportunities."
        val socialImageUrl = "$baseUrl/static/images/profile-social.jpg"
        val jsonLd = createPersonSchema(personalInfo, baseUrl, socialImageUrl, metaDescription)

        return PageMetadata(
            title = "${personalInfo.name} - ${personalInfo.title}",
            description = metaDescription,
            canonicalUrl = "$baseUrl/",
            socialImageUrl = socialImageUrl,
            ogType = "website",
            publishedTime = "2025-11-02T00:00:00+01:00",
            structuredDataJsonLd = jsonLd,
        )
    }

    private fun buildProjectMetadata(
        authorName: String,
        project: PersonalProject,
    ): PageMetadata {
        val canonical = "$baseUrl/projects/${project.slug}"
        return PageMetadata(
            title = "${project.name} – $authorName",
            description = project.summary,
            canonicalUrl = canonical,
            socialImageUrl = "$baseUrl/static/images/profile-social.jpg",
            ogType = "article",
            structuredDataJsonLd = createProjectSchema(project, canonical, authorName),
        )
    }

    private fun createPersonSchema(
        personalInfo: PersonalInfo,
        baseUrl: String,
        socialImageUrl: String,
        description: String,
    ): String {
        return """
            {
              \"@context\": \"https://schema.org\",
              \"@type\": \"Person\",
              \"name\": \"${personalInfo.name}\",
              \"jobTitle\": \"${personalInfo.title}\",
              \"description\": \"${description.replace("\"", "\\\\\"")}\",
              \"email\": \"mailto:${personalInfo.email}\",
              \"telephone\": \"${personalInfo.phone}\",
              \"url\": \"$baseUrl\",
              \"image\": \"$socialImageUrl\",
              \"sameAs\": [
                \"${personalInfo.linkedin}\",
                \"${personalInfo.github}\"
              ],
              \"address\": {
                \"@type\": \"PostalAddress\",
                \"addressLocality\": \"${personalInfo.location}\"
              }
            }
            """.trimIndent()
    }

    private fun createProjectSchema(
        project: PersonalProject,
        canonicalUrl: String,
        authorName: String,
    ): String {
        return """
            {
              \"@context\": \"https://schema.org\",
              \"@type\": \"CreativeWork\",
              \"name\": \"${project.name}\",
              \"description\": \"${project.summary.replace("\"", "\\\\\"")}\",
              \"url\": \"$canonicalUrl\",
              \"author\": {
                \"@type\": \"Person\",
                \"name\": \"$authorName\"
              },
              \"genre\": \"${project.category}\",
              \"headline\": \"${project.name}\"
            }
            """.trimIndent()
    }
}
