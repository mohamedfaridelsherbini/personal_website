package com.personalwebsite.infrastructure.content

import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

/**
 * Locates repository paths from any working directory.
 *
 * Gradle runs the app from `bootstrap/`, so a plain relative path would create and read a stray copy of the
 * content under `bootstrap/infrastructure/`. Walking up to the repository root keeps one source of truth.
 */
internal object ContentRoots {
    private const val MAX_LEVELS = 5

    fun findUp(relative: String): Path? {
        var dir: Path? = Paths.get("").toAbsolutePath().normalize()
        repeat(MAX_LEVELS) {
            val candidate = dir?.resolve(relative)
            if (candidate != null && Files.exists(candidate)) return candidate
            dir = dir?.parent
        }
        return null
    }
}
