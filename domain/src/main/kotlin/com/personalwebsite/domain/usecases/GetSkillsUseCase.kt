package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.Skill
import com.personalwebsite.domain.repositories.SkillRepository

/**
 * Use case for getting skills
 */
class GetSkillsUseCase(
    private val skillRepository: SkillRepository,
) {
    suspend operator fun invoke(): List<Skill> {
        return skillRepository.getSkills()
    }
}
