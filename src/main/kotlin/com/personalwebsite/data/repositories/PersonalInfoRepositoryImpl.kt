package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.repositories.PersonalInfoRepository
import mu.KotlinLogging

/**
 * Personal info repository - just returns hardcoded data for now
 */
class PersonalInfoRepositoryImpl : PersonalInfoRepository {
    
    private val logger = KotlinLogging.logger {}
    
    override suspend fun getPersonalInfo(): PersonalInfo {
        logger.info { "Getting personal info" }
        
        // Just hardcoded for now - might add a database later
        val personalInfo = PersonalInfo(
            name = "Mohamed ElSherbini",
            title = "Senior Android Engineer",
            summary = "Senior Android Engineer with 8+ years shipping Kotlin-first experiences across fintech, automation, and productivity products. Obsessed with resilient architecture, automation pipelines, and user journeys that balance compliance with delight.",
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
