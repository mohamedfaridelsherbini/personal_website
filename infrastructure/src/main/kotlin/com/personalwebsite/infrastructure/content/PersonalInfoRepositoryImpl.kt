package com.personalwebsite.infrastructure.content

import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.repositories.PersonalInfoRepository
import mu.KotlinLogging

/**
 * Loads personal info from structured JSON content.
 */
class PersonalInfoRepositoryImpl(
    private val contentLoader: ContentLoader
) : PersonalInfoRepository {

    private val logger = KotlinLogging.logger {}

    override suspend fun getPersonalInfo(): PersonalInfo {
        logger.info { "Loading personal info from content store" }
        val info: PersonalInfo = contentLoader.load("content/personal-info.json")
        logger.debug { "Personal info loaded: ${info.name}" }
        return info
    }
}
