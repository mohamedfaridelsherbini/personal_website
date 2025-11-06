package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.repositories.PersonalProjectRepository

/**
 * Use case for retrieving personal projects
 */
class GetPersonalProjectsUseCase(
    private val personalProjectRepository: PersonalProjectRepository,
) {
    suspend operator fun invoke(): List<PersonalProject> {
        return personalProjectRepository.getPersonalProjects()
    }
}
