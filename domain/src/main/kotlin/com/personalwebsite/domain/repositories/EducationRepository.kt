package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.Education

interface EducationRepository {
    suspend fun getEducation(): List<Education>
}
