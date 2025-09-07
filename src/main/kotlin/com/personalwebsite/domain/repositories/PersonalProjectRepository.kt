package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.PersonalProject

/**
 * Repository interface for personal projects
 * Follows Dependency Inversion Principle - defines abstraction for data access
 */
interface PersonalProjectRepository {
    suspend fun getPersonalProjects(): List<PersonalProject>
}
