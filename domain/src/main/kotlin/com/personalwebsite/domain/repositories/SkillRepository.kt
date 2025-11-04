package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.Skill

/**
 * Repository interface for skills
 */
interface SkillRepository {
    suspend fun getSkills(): List<Skill>
}
