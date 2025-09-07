package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.PersonalInfo

/**
 * Repository interface for personal information
 * Follows Dependency Inversion Principle - depends on abstraction, not concrete implementation
 */
interface PersonalInfoRepository {
    suspend fun getPersonalInfo(): PersonalInfo
}
