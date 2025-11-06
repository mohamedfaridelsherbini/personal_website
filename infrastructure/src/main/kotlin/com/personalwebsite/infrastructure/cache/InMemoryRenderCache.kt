package com.personalwebsite.infrastructure.cache

import com.personalwebsite.application.website.ports.RenderCache
import java.util.concurrent.ConcurrentHashMap

/**
 * Naive in-memory cache that stores rendered HTML by route.
 */
class InMemoryRenderCache : RenderCache {
    private val cache = ConcurrentHashMap<String, String>()

    override suspend fun getOrPut(
        key: String,
        block: suspend () -> String,
    ): String {
        val cached = cache[key]
        if (cached != null) {
            return cached
        }

        val fresh = block()
        cache[key] = fresh
        return fresh
    }

    override fun peek(key: String): String? = cache[key]

    override fun invalidate(key: String) {
        cache.remove(key)
    }

    override fun clear() {
        cache.clear()
    }
}
