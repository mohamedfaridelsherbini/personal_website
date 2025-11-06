package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.repositories.PersonalInfoRepository

/**
 * Use case for getting personal information
 */
class GetPersonalInfoUseCase(
    private val personalInfoRepository: PersonalInfoRepository,
) {
    suspend operator fun invoke(): PersonalInfo {
        return personalInfoRepository.getPersonalInfo()
    }
}
