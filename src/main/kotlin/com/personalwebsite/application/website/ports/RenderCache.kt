package com.personalwebsite.application.website.ports

/**
 * Outbound port for caching rendered HTML pages.
 */
interface RenderCache {
    fun peek(key: String): String?
    suspend fun getOrPut(key: String, block: suspend () -> String): String
    fun invalidate(key: String)
    fun clear()
}
