package com.personalwebsite.domain.repositories

import com.personalwebsite.domain.entities.Language

/**
 * Repository interface for languages
 * Follows Dependency Inversion Principle - depends on abstraction, not concrete implementation
 */
interface LanguageRepository {
    suspend fun getLanguages(): List<Language>
}
