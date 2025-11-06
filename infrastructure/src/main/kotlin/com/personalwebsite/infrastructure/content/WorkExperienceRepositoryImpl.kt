package com.personalwebsite.infrastructure.content

import com.personalwebsite.domain.entities.WorkExperience
import com.personalwebsite.domain.repositories.WorkExperienceRepository

/**
 * Work experience repository backed by structured content files.
 */
class WorkExperienceRepositoryImpl(
    private val contentLoader: ContentLoader,
) : WorkExperienceRepository {
    override suspend fun getWorkExperience(): List<WorkExperience> = contentLoader.load("content/work-experience.json")
}
