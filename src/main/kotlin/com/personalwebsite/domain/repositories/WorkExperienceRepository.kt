package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.WorkExperience

/**
 * Repository interface for work experience
 */
interface WorkExperienceRepository {
    suspend fun getWorkExperience(): List<WorkExperience>
}
