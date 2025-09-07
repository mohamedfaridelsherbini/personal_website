package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.repositories.PersonalInfoRepository
import mu.KotlinLogging

/**
 * Concrete implementation of PersonalInfoRepository
 * Follows Dependency Inversion Principle - implements the abstraction
 * Follows Single Responsibility Principle - only handles personal info data
 */
class PersonalInfoRepositoryImpl : PersonalInfoRepository {
    
    private val logger = KotlinLogging.logger {}
    
    override suspend fun getPersonalInfo(): PersonalInfo {
        logger.info { "Fetching personal information" }
        
        // In a real application, this would fetch from a database or API
        // For now, we return static data
        val personalInfo = PersonalInfo(
            name = "Mohamed ElSherbini",
            title = "Senior Android Engineer",
            summary = "Senior Android Engineer with 8+ years of experience building robust mobile applications using Kotlin and Java.",
            location = "Augsburg, Bavaria, Germany",
            phone = "+49 172 6079066",
            email = "m.farid.shawky@gmail.com",
            linkedin = "https://linkedin.com/in/mofaridelsherbini",
            github = "https://github.com/mohamedfaridelsherbini"
        )
        
        logger.debug { "Personal information fetched successfully: ${personalInfo.name}" }
        return personalInfo
    }
}
