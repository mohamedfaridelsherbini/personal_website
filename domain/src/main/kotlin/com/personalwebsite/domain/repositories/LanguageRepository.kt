package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.Language

/**
 * Repository interface for languages
 */
interface LanguageRepository {
    suspend fun getLanguages(): List<Language>
}
