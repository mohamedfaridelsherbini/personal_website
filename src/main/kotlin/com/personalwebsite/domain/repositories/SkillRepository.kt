package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.Skill

/**
 * Repository interface for skills
 * Follows Dependency Inversion Principle - depends on abstraction, not concrete implementation
 */
interface SkillRepository {
    suspend fun getSkills(): List<Skill>
}
