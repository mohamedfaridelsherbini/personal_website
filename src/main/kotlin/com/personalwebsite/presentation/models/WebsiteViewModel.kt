package com.personalwebsite.presentation.models

import com.personalwebsite.domain.entities.*

/**
 * ViewModel for the website
 * Follows Single Responsibility Principle - only handles presentation data
 * Follows Open/Closed Principle - can be extended without modification
 */
data class WebsiteViewModel(
    val personalInfo: PersonalInfo,
    val skills: List<Skill>,
    val workExperience: List<WorkExperience>,
    val personalProjects: List<PersonalProject>,
    val languages: List<Language>
)
