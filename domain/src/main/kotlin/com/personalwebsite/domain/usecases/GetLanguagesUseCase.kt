package com.personalwebsite.domain.usecases

import com.personalwebsite.domain.entities.Language
import com.personalwebsite.domain.repositories.LanguageRepository

/**
 * Use case for getting languages
 */
class GetLanguagesUseCase(
    private val languageRepository: LanguageRepository
) {
    suspend operator fun invoke(): List<Language> {
        return languageRepository.getLanguages()
    }
}
