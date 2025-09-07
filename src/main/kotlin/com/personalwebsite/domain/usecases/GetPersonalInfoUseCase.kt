package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.repositories.PersonalInfoRepository

/**
 * Use case for getting personal information
 * Follows Single Responsibility Principle - only handles getting personal info
 * Follows Dependency Inversion Principle - depends on repository abstraction
 */
class GetPersonalInfoUseCase(
    private val personalInfoRepository: PersonalInfoRepository
) {
    suspend operator fun invoke(): PersonalInfo {
        return personalInfoRepository.getPersonalInfo()
    }
}
