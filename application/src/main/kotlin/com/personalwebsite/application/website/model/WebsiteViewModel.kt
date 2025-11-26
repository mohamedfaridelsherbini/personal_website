package com.personalwebsite.application.website.model

import com.personalwebsite.domain.entities.Education
import com.personalwebsite.domain.entities.Language
import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.entities.Skill
import com.personalwebsite.domain.entities.WorkExperience

/**
 * ViewModel for the website
 * Just holds the data needed for the view
 */
data class WebsiteViewModel(
    val personalInfo: PersonalInfo,
    val skills: List<Skill>,
    val workExperience: List<WorkExperience>,
    val personalProjects: List<PersonalProject>,
    val languages: List<Language>,
    val education: List<Education>,
)
