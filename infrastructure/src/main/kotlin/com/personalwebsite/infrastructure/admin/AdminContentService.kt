package com.personalwebsite.infrastructure.admin

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.personalwebsite.application.website.ports.RenderCache
import com.personalwebsite.infrastructure.content.ContentLoader
import mu.KotlinLogging
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardOpenOption

class AdminContentService(
    private val gson: Gson,
    private val contentLoader: ContentLoader,
    private val renderCache: RenderCache,
    contentRootOverride: Path? = System.getenv("CONTENT_DIR")?.takeIf { it.isNotBlank() }?.let { Paths.get(it) },
    resumeDir: Path = Paths.get(System.getenv("RESUME_DIR") ?: "bootstrap/src/main/resources/static/files"),
    private val resumeFileName: String = System.getenv("RESUME_FILENAME") ?: "Mohamed_ElSherbini_Resume.pdf",
) {
    data class ContentSection(
        val id: String,
        val label: String,
        val fileName: String,
        val path: Path,
    )

    data class ContentPayload(
        val section: ContentSection,
        val json: String,
    )

    private val logger = KotlinLogging.logger {}
    private val prettyGson: Gson = GsonBuilder().setPrettyPrinting().create()
    private val contentRoot: Path = resolveContentRoot(contentRootOverride)
    private val resumeDir: Path = resumeDir.toAbsolutePath().normalize()

    private val sections =
        listOf(
            ContentSection("personal-info", "Personal Info", "personal-info.json", contentRoot.resolve("personal-info.json")),
            ContentSection("skills", "Skills", "skills.json", contentRoot.resolve("skills.json")),
            ContentSection("work-experience", "Work Experience", "work-experience.json", contentRoot.resolve("work-experience.json")),
            ContentSection("personal-projects", "Personal Projects", "personal-projects.json", contentRoot.resolve("personal-projects.json")),
            ContentSection("languages", "Languages", "languages.json", contentRoot.resolve("languages.json")),
        )

    init {
        Files.createDirectories(this.contentRoot)
        Files.createDirectories(this.resumeDir)
        bootstrapContentFiles()
    }

    fun sections(): List<ContentSection> = sections

    fun loadContent(): List<ContentPayload> = sections.map { ContentPayload(it, readSection(it)) }

    fun updateContent(sectionId: String, rawJson: String): ContentPayload {
        val section =
            sections.find { it.id == sectionId }
                ?: throw IllegalArgumentException("Unknown content section: $sectionId")

        val jsonElement =
            try {
                gson.fromJson(rawJson, JsonElement::class.java)
            } catch (error: Exception) {
                throw IllegalArgumentException("Invalid JSON: ${error.message}", error)
            }

        val prettyJson = prettyGson.toJson(jsonElement)

        writeFile(section.path, prettyJson)
        contentLoader.clearCache()
        renderCache.clear()
        logger.info { "Updated ${section.label} content and cleared caches" }

        return ContentPayload(section, prettyJson)
    }

    fun replaceResume(fileName: String?, bytes: ByteArray): Path {
        if (bytes.isEmpty()) {
            throw IllegalArgumentException("Uploaded resume file is empty")
        }

        val target = resumeDir.resolve(resumeFileName)
        Files.write(target, bytes, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
        renderCache.clear()
        logger.info { "Replaced resume with ${fileName ?: resumeFileName}, stored at $target" }
        return target
    }

    fun resumeTargetPath(): Path = resumeDir.resolve(resumeFileName)
    fun resumeDirectory(): Path = resumeDir

    private fun readSection(section: ContentSection): String {
        val path = section.path
        if (Files.exists(path)) {
            return Files.readString(path)
        }

        val resourcePath = "content/${section.fileName}"
        val stream = ContentLoader::class.java.classLoader.getResourceAsStream(resourcePath)

        if (stream != null) {
            val content = stream.bufferedReader().use { it.readText() }
            writeFile(path, content)
            return content
        }

        throw IllegalStateException("Missing content file ${section.fileName} and no classpath resource found")
    }

    private fun writeFile(path: Path, content: String) {
        Files.write(path, content.toByteArray(Charsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)
    }

    private fun resolveContentRoot(explicit: Path?): Path {
        val candidates =
            listOfNotNull(
                explicit,
                Paths.get("infrastructure/src/main/resources/content"),
                Paths.get("src/main/resources/content"),
                Paths.get("content"),
            )

        val existing = candidates.firstOrNull { Files.exists(it) }
        return (existing ?: candidates.first()).toAbsolutePath().normalize()
    }

    private fun bootstrapContentFiles() {
        sections.forEach { section ->
            if (!Files.exists(section.path)) {
                val resourcePath = "content/${section.fileName}"
                val stream = ContentLoader::class.java.classLoader.getResourceAsStream(resourcePath)
                if (stream != null) {
                    val content = stream.bufferedReader().use { it.readText() }
                    writeFile(section.path, content)
                    logger.info { "Bootstrapped ${section.fileName} into ${section.path}" }
                }
            }
        }
    }
}
