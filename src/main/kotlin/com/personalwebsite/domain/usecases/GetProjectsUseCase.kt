package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.Project
import com.personalwebsite.domain.repositories.ProjectRepository

/**
 * Use case for getting projects
 * Follows Single Responsibility Principle - only handles getting projects
 * Follows Dependency Inversion Principle - depends on repository abstraction
 */
class GetProjectsUseCase(
    private val projectRepository: ProjectRepository
) {
    suspend operator fun invoke(): List<Project> {
        return projectRepository.getProjects()
    }
}
