package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.PersonalProject

/**
 * Repository interface for personal projects
 */
interface PersonalProjectRepository {
    suspend fun getPersonalProjects(): List<PersonalProject>
}
