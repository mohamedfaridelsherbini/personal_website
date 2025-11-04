package com.personalwebsite.data.repositories

import com.personalwebsite.data.content.ContentLoader
import com.personalwebsite.domain.entities.Skill
import com.personalwebsite.domain.repositories.SkillRepository

/**
 * Skills repository backed by structured content.
 */
class SkillRepositoryImpl(
    private val contentLoader: ContentLoader
) : SkillRepository {

    override suspend fun getSkills(): List<Skill> = contentLoader.load("content/skills.json")
}
