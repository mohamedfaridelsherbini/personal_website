package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Repository interface for personal information
 */
interface PersonalInfoRepository {
    suspend fun getPersonalInfo(): PersonalInfo
}
