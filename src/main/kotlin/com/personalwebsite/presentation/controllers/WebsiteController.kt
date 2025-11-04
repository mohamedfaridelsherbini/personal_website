package com.personalwebsite.presentation.controllers

import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.usecases.GetPersonalInfoUseCase
import com.personalwebsite.domain.usecases.GetSkillsUseCase
import com.personalwebsite.domain.usecases.GetWorkExperienceUseCase
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import com.personalwebsite.domain.usecases.GetLanguagesUseCase
import com.personalwebsite.presentation.cache.ContentCache
import com.personalwebsite.presentation.models.PageMetadata
import com.personalwebsite.presentation.models.PageModel
import com.personalwebsite.presentation.models.WebsiteViewModel
import com.personalwebsite.presentation.views.WebsiteView
import mu.KotlinLogging

/**
 * Controller for the website
 * Handles getting all the data and passing it to the view
 */
class WebsiteController(
    private val getPersonalInfoUseCase: GetPersonalInfoUseCase,
    private val getSkillsUseCase: GetSkillsUseCase,
    private val getWorkExperienceUseCase: GetWorkExperienceUseCase,
    private val getPersonalProjectsUseCase: GetPersonalProjectsUseCase,
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val websiteView: WebsiteView,
    private val contentCache: ContentCache
) {
    
    private val logger = KotlinLogging.logger {}
    private val baseUrl = "https://www.mohamedfaridelsherbini.com"
    
    /**
     * Loads all website data and renders the view
     * This used to be much simpler but I added more sections over time
     */
    suspend fun loadWebsite(): String {
        val cacheKey = "GET:/"
        contentCache.peek(cacheKey)?.let {
            logger.debug { "Serving cached home page" }
            return it
        }

        logger.info { "Cache miss for home page, generating HTML" }

        return try {
            contentCache.getOrPut(cacheKey) {
                val personalInfo = getPersonalInfoUseCase()
                val skills = getSkillsUseCase()
                val workExperience = getWorkExperienceUseCase()
                val personalProjects = getPersonalProjectsUseCase()
                val languages = getLanguagesUseCase()

                logger.debug {
                    "Loaded ${skills.size} skill categories, ${workExperience.size} work experiences, ${personalProjects.size} projects"
                }

                val viewModel = WebsiteViewModel(
                    personalInfo = personalInfo,
                    skills = skills,
                    workExperience = workExperience,
                    personalProjects = personalProjects,
                    languages = languages
                )

                val metadata = buildHomeMetadata(personalInfo)
                val page = PageModel.Home(
                    metadata = metadata,
                    site = viewModel
                )

                websiteView.render(page)
            }
        } catch (e: Exception) {
            logger.error(e) { "Failed to load website data - ${e.message}" }
            websiteView.renderError("Failed to load website data: ${e.message}")
        }
    }

    suspend fun loadProject(slug: String): String {
        val cacheKey = "GET:/projects/$slug"
        contentCache.peek(cacheKey)?.let {
            logger.debug { "Serving cached project page for $slug" }
            return it
        }

        logger.info { "Cache miss for project detail $slug, generating HTML" }

        val personalInfo = getPersonalInfoUseCase()

        return try {
            contentCache.getOrPut(cacheKey) {
                val skills = getSkillsUseCase()
                val workExperience = getWorkExperienceUseCase()
                val personalProjects = getPersonalProjectsUseCase()
                val languages = getLanguagesUseCase()

                val project = personalProjects.find { it.slug == slug }
                    ?: throw NoSuchElementException("Project not found: $slug")

                val viewModel = WebsiteViewModel(
                    personalInfo = personalInfo,
                    skills = skills,
                    workExperience = workExperience,
                    personalProjects = personalProjects,
                    languages = languages
                )

                val metadata = buildProjectMetadata(personalInfo.name, project)
                val page = PageModel.Project(
                    project = project,
                    metadata = metadata,
                    site = viewModel
                )

                websiteView.render(page)
            }
        } catch (e: NoSuchElementException) {
            contentCache.invalidate(cacheKey)
            throw e
        }
    }

    fun renderError(message: String) = websiteView.renderError(message)

    private fun buildHomeMetadata(personalInfo: PersonalInfo): PageMetadata {
        val metaDescription = "Senior Android engineer crafting Kotlin and Jetpack Compose experiences for fintech and product teams across Europe. Currently at Check24 in Munich, open to remote consulting and leadership opportunities."
        val socialImageUrl = "$baseUrl/static/images/profile-social.jpg"
        val jsonLd = createPersonSchema(personalInfo, baseUrl, socialImageUrl, metaDescription)

        return PageMetadata(
            title = "${personalInfo.name} - ${personalInfo.title}",
            description = metaDescription,
            canonicalUrl = "$baseUrl/",
            socialImageUrl = socialImageUrl,
            ogType = "website",
            publishedTime = "2025-11-02T00:00:00+01:00",
            structuredDataJsonLd = jsonLd
        )
    }

    private fun buildProjectMetadata(authorName: String, project: PersonalProject): PageMetadata {
        val canonical = "$baseUrl/projects/${project.slug}"
        return PageMetadata(
            title = "${project.name} – $authorName",
            description = project.summary,
            canonicalUrl = canonical,
            socialImageUrl = "$baseUrl/static/images/profile-social.jpg",
            ogType = "article",
            structuredDataJsonLd = createProjectSchema(project, canonical, authorName)
        )
    }

    private fun createPersonSchema(
        personalInfo: PersonalInfo,
        baseUrl: String,
        socialImageUrl: String,
        description: String
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
        authorName: String
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
