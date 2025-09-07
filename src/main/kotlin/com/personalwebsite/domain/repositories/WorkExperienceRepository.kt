package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.WorkExperience

/**
 * Repository interface for work experience
 * Follows Dependency Inversion Principle - depends on abstraction, not concrete implementation
 */
interface WorkExperienceRepository {
    suspend fun getWorkExperience(): List<WorkExperience>
}
