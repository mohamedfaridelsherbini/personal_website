package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.WorkExperience
import com.personalwebsite.domain.repositories.WorkExperienceRepository

/**
 * Use case for getting work experience
 */
class GetWorkExperienceUseCase(
    private val workExperienceRepository: WorkExperienceRepository
) {
    suspend operator fun invoke(): List<WorkExperience> {
        return workExperienceRepository.getWorkExperience()
    }
}
