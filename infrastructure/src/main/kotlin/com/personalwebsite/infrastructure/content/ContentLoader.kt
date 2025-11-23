package com.personalwebsite.infrastructure.content

import com.google.gson.Gson
import com.google.gson.JsonParseException
import com.google.gson.reflect.TypeToken
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap

/**
 * Loads structured content from the resources folder and keeps it cached in-memory.
 * This keeps repositories declarative and allows the content team to edit JSON/YAML without touching Kotlin.
 */
class ContentLoader(
    @PublishedApi internal val gson: Gson,
    @PublishedApi internal val classLoader: ClassLoader = ContentLoader::class.java.classLoader,
) {
    private val diskContentRoot: Path? =
        listOfNotNull(
            System.getenv("CONTENT_DIR")?.takeIf { it.isNotBlank() }?.let { Paths.get(it) },
            Paths.get("infrastructure/src/main/resources"),
            Paths.get("src/main/resources"),
        ).map { it.toAbsolutePath().normalize() }
            .firstOrNull { Files.exists(it) }

    @PublishedApi
    internal val cache = ConcurrentHashMap<String, Any>()

    /**
     * Load a content file located under `resources/`.
     * Example: load<List<Skill>>("content/skills.json")
     */
    inline fun <reified T> load(path: String): T {
        return cache.computeIfAbsent(path) { key ->
            val stream = openStream(key) ?: throw IllegalArgumentException("Content resource not found: $key")

            stream.use { input ->
                InputStreamReader(input, StandardCharsets.UTF_8).use { reader ->
                    val type = object : TypeToken<T>() {}.type
                    try {
                        gson.fromJson<T>(reader, type) as Any
                    } catch (error: JsonParseException) {
                        throw IllegalStateException("Failed to parse content resource $key", error)
                    }
                }
            }
        } as T
    }

    fun clearCache() {
        cache.clear()
    }

    @PublishedApi
    internal fun openStream(path: String): InputStream? {
        diskContentRoot?.resolve(path)?.let { resolved ->
            if (Files.exists(resolved)) {
                return Files.newInputStream(resolved)
            }
        }
        return classLoader.getResourceAsStream(path)
    }
}
