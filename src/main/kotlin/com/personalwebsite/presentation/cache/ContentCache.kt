package com.personalwebsite.presentation.cache

import java.util.concurrent.ConcurrentHashMap

/**
 * Naive in-memory cache that stores rendered HTML by route.
 */
class ContentCache {

    private val cache = ConcurrentHashMap<String, String>()

    suspend fun getOrPut(key: String, block: suspend () -> String): String {
        val cached = cache[key]
        if (cached != null) {
            return cached
        }

        val fresh = block()
        cache[key] = fresh
        return fresh
    }

    fun peek(key: String): String? = cache[key]

    fun invalidate(key: String) {
        cache.remove(key)
    }

    fun clear() {
        cache.clear()
    }
}
