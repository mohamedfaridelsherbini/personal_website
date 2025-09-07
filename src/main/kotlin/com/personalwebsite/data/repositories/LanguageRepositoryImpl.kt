package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.Language
import com.personalwebsite.domain.repositories.LanguageRepository

/**
 * Concrete implementation of LanguageRepository
 * Follows Dependency Inversion Principle - implements the abstraction
 * Follows Single Responsibility Principle - only handles language data
 */
class LanguageRepositoryImpl : LanguageRepository {
    
    override suspend fun getLanguages(): List<Language> {
        // In a real application, this would fetch from a database or API
        return listOf(
            Language(name = "Arabic", level = "Native"),
            Language(name = "English", level = "Fluent"),
            Language(name = "German", level = "A1 (Currently studying A2)")
        )
    }
}
