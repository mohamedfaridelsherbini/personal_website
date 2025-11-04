package com.personalwebsite.presentation.controllers

import com.personalwebsite.domain.usecases.GetPersonalInfoUseCase
import com.personalwebsite.domain.usecases.GetSkillsUseCase
import com.personalwebsite.domain.usecases.GetWorkExperienceUseCase
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import com.personalwebsite.domain.usecases.GetLanguagesUseCase
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
    private val websiteView: WebsiteView
) {
    
    private val logger = KotlinLogging.logger {}
    
    /**
     * Loads all website data and renders the view
     * This used to be much simpler but I added more sections over time
     */
    suspend fun loadWebsite(): String {
        logger.info { "Loading website data" }

        return try {
            // Get all the data we need - this could probably be optimized
            val personalInfo = getPersonalInfoUseCase()
            val skills = getSkillsUseCase()
            val workExperience = getWorkExperienceUseCase()
            val personalProjects = getPersonalProjectsUseCase()
            val languages = getLanguagesUseCase()
            
            // Debug: log some stats
            logger.debug { "Loaded ${skills.size} skill categories, ${workExperience.size} work experiences, ${personalProjects.size} projects" }
            
            val viewModel = WebsiteViewModel(
                personalInfo = personalInfo,
                skills = skills,
                workExperience = workExperience,
                personalProjects = personalProjects,
                languages = languages
            )
            
            logger.info { "Website data loaded successfully for ${personalInfo.name}" }
            websiteView.render(viewModel)
        } catch (e: Exception) {
            logger.error(e) { "Failed to load website data - ${e.message}" }
            websiteView.renderError("Failed to load website data: ${e.message}")
        }
    }

    suspend fun loadProject(slug: String): String {
        logger.info { "Loading project detail for slug=$slug" }

        val personalInfo = getPersonalInfoUseCase()
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

        return websiteView.renderProject(project, viewModel)
    }

    fun renderError(message: String) = websiteView.renderError(message)
}
