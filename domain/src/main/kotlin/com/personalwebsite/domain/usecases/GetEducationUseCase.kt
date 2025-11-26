package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.Education
import com.personalwebsite.domain.repositories.EducationRepository

class GetEducationUseCase(private val educationRepository: EducationRepository) {
    suspend operator fun invoke(): List<Education> {
        return educationRepository.getEducation()
    }
}
