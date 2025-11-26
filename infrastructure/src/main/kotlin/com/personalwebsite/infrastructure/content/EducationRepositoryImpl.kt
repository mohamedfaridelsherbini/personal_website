package com.personalwebsite.infrastructure.content

import com.personalwebsite.domain.entities.Education
import com.personalwebsite.domain.repositories.EducationRepository
import mu.KotlinLogging

class EducationRepositoryImpl(
    private val contentLoader: ContentLoader,
) : EducationRepository {
    private val logger = KotlinLogging.logger {}

    override suspend fun getEducation(): List<Education> {
        logger.info { "Loading education from content store" }
        val education: List<Education> = contentLoader.load("content/education.json")
        logger.debug { "Loaded ${education.size} education entries" }
        return education
    }
}
