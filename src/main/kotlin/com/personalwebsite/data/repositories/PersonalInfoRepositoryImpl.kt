package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.repositories.PersonalInfoRepository
import mu.KotlinLogging

/**
 * Personal info repository - just returns hardcoded data for now
 * TODO: Maybe connect to a CMS or database later
 */
class PersonalInfoRepositoryImpl : PersonalInfoRepository {
    
    private val logger = KotlinLogging.logger {}
    
    override suspend fun getPersonalInfo(): PersonalInfo {
        logger.info { "Getting personal info" }
        
        // Just hardcoded for now - might add a database later
        val personalInfo = PersonalInfo(
            name = "Mohamed ElSherbini",
            title = "Senior Android Engineer",
            summary = "Senior Android Engineer with 8+ years of experience building robust mobile applications using Kotlin and Java. Currently working at Check24 in Munich, focusing on financial comparison apps.",
            location = "Augsburg, Bavaria, Germany",
            phone = "+49 172 6079066",
            email = "m.farid.shawky@gmail.com",
            linkedin = "https://linkedin.com/in/mofaridelsherbini",
            github = "https://github.com/mohamedfaridelsherbini"
        )
        
        logger.debug { "Personal info loaded: ${personalInfo.name}" }
        return personalInfo
    }
}
