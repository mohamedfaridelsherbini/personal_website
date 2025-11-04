package com.personalwebsite.data.repositories

import com.personalwebsite.data.content.ContentLoader
import com.personalwebsite.domain.entities.Language
import com.personalwebsite.domain.repositories.LanguageRepository

/**
 * Loads languages from structured content.
 */
class LanguageRepositoryImpl(
    private val contentLoader: ContentLoader
) : LanguageRepository {

    override suspend fun getLanguages(): List<Language> =
        contentLoader.load("content/languages.json")
}
