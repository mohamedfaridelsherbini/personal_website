package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.Language
import com.personalwebsite.domain.repositories.LanguageRepository

/**
 * Use case for getting languages
 * Follows Single Responsibility Principle - only handles getting languages
 * Follows Dependency Inversion Principle - depends on repository abstraction
 */
class GetLanguagesUseCase(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(): List<Language> {
        return languageRepository.getLanguages()
    }
}
