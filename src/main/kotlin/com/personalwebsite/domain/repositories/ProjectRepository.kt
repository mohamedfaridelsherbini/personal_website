package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.Project

/**
 * Repository interface for projects
 * Follows Dependency Inversion Principle - depends on abstraction, not concrete implementation
 */
interface ProjectRepository {
    suspend fun getProjects(): List<Project>
}
