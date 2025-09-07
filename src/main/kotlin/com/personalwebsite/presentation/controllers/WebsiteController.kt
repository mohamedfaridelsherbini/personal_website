package com.personalwebsite.presentation.controllers

import com.personalwebsite.domain.usecases.*
import com.personalwebsite.presentation.models.WebsiteViewModel
import com.personalwebsite.presentation.views.WebsiteView
import mu.KotlinLogging

/**
 * Controller for the website
 * Follows Single Responsibility Principle - only handles business logic coordination
 * Follows Dependency Inversion Principle - depends on use case abstractions
 * Follows Open/Closed Principle - can be extended without modification
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
     * Follows Single Responsibility Principle - only handles data loading and view rendering
     */
    suspend fun loadWebsite(): String {
        logger.info { "Loading website data" }
        
        return try {
            val personalInfo = getPersonalInfoUseCase()
            val skills = getSkillsUseCase()
            val workExperience = getWorkExperienceUseCase()
            val personalProjects = getPersonalProjectsUseCase()
            val languages = getLanguagesUseCase()
            
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
            logger.error(e) { "Failed to load website data" }
            websiteView.renderError("Failed to load website data: ${e.message}")
        }
    }
}
