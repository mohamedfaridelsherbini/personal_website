package com.personalwebsite.infrastructure.admin

import com.google.gson.Gson
import com.personalwebsite.infrastructure.admin.AdminSectionService.Result
import com.personalwebsite.infrastructure.cache.InMemoryRenderCache
import com.personalwebsite.infrastructure.content.ContentLoader
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class AdminSectionServiceTest {
    private fun service(): AdminSectionService {
        val dir = Files.createTempDirectory("sections")
        listOf("skills", "work-experience", "education", "personal-projects").forEach { Files.writeString(dir.resolve("$it.json"), "[]") }
        Files.writeString(dir.resolve("languages.json"), """[{"name":"Arabic","level":"Native","extra":"keep"}]""")
        Files.writeString(dir.resolve("personal-info.json"), """{"name":"Me","title":"Dev"}""")
        val gson = Gson()
        val content = AdminContentService(gson, ContentLoader(gson), InMemoryRenderCache(), dir, Files.createTempDirectory("resume"))
        return AdminSectionService(content)
    }

    private fun entries(
        service: AdminSectionService,
        id: String,
    ) = service.view().first { it.schema.id == id }.entries

    @Test
    fun addsEditsAndDeletesItems() {
        val service = service()
        assertIs<Result.Saved>(service.add("languages", mapOf("name" to "German", "level" to "A1")))
        assertEquals(listOf("Arabic", "German"), entries(service, "languages").map { it.title })

        assertIs<Result.Saved>(service.update("languages", 0, mapOf("name" to "Arabic", "level" to "Mother tongue")))
        assertEquals("Mother tongue", entries(service, "languages")[0].values["level"])

        assertIs<Result.Saved>(service.delete("languages", 1))
        assertEquals(listOf("Arabic"), entries(service, "languages").map { it.title })
    }

    @Test
    fun rejectsMissingRequiredFieldsAndBadIndexes() {
        val service = service()
        assertIs<Result.Invalid>(service.add("languages", mapOf("level" to "A1")))
        assertIs<Result.Invalid>(service.delete("languages", 5))
        assertIs<Result.Invalid>(service.add("personal-info", mapOf("name" to "x")))
    }

    @Test
    fun linesBecomeListsAndPersonalInfoIsEditable() {
        val service = service()
        service.add("skills", mapOf("category" to "Langs", "items" to "Kotlin\n\nJava "))
        assertEquals("Kotlin\nJava", entries(service, "skills")[0].values["items"])
        assertIs<Result.Saved>(service.update("personal-info", 0, mapOf("name" to "Me", "title" to "Lead")))
        assertEquals("Lead", entries(service, "personal-info")[0].values["title"])
    }
}
