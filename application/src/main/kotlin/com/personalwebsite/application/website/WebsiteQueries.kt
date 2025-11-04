package com.personalwebsite.application.website

/**
 * Inbound port describing the website pages that can be rendered.
 * Infrastructure adapters (e.g., Ktor routes) depend on this abstraction.
 */
interface WebsiteQueries {
    suspend fun renderHome(): String
    suspend fun renderProject(slug: String): String
    fun renderError(message: String): String
}
