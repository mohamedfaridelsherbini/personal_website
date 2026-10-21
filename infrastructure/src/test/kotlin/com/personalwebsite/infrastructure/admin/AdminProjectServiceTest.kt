package com.personalwebsite.infrastructure.admin

import com.google.gson.Gson
import com.personalwebsite.infrastructure.admin.AdminProjectService.Form
import com.personalwebsite.infrastructure.admin.AdminProjectService.LinkField
import com.personalwebsite.infrastructure.admin.AdminProjectService.Result
import com.personalwebsite.infrastructure.cache.InMemoryRenderCache
import com.personalwebsite.infrastructure.content.ContentLoader
import java.nio.file.Files
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class AdminProjectServiceTest {
    private val projectsJson =
        """
        [
          {"name":"Alpha","slug":"alpha","description":"A","techStack":"Kotlin","category":"App","featured":true,"metrics":["1K users"]},
          {"name":"Beta","slug":"beta","description":"B","techStack":"Kotlin","category":"App"},
          {"name":"Gamma","slug":"gamma","description":"C","techStack":"Kotlin","category":"App"}
        ]
        """.trimIndent()

    private fun service(): AdminProjectService {
        val dir = Files.createTempDirectory("projects")
        listOf("personal-info", "skills", "work-experience", "education", "languages").forEach {
            Files.writeString(dir.resolve("$it.json"), "[]")
        }
        Files.writeString(dir.resolve("personal-projects.json"), projectsJson)
        val gson = Gson()
        val content = AdminContentService(gson, ContentLoader(gson), InMemoryRenderCache(), dir, Files.createTempDirectory("resume"))
        return AdminProjectService(content)
    }

    private fun form(
        slug: String,
        name: String = "New",
        master: Boolean = false,
        order: String = "",
    ) = Form(name = name, slug = slug, master = master, order = order, description = "Described")

    @Test
    fun listsPlacementAndOrderWithinEachSection() {
        val rows = service().rows()
        assertEquals(listOf("alpha" to 1, "beta" to 1, "gamma" to 2), rows.map { it.slug to it.order })
        assertTrue(rows.first { it.slug == "alpha" }.master)
    }

    @Test
    fun rejectsMissingFieldsDuplicateSlugsAndUnsafeLinks() {
        val result =
            service().create(
                Form(slug = "alpha", order = "-1", links = listOf(LinkField("x", "javascript:alert(1)"))),
            )
        val errors = assertIs<Result.Invalid>(result).errors
        assertEquals(setOf("name", "slug", "description", "order", "link0"), errors.keys)
        assertEquals("This slug is already in use.", errors["slug"])
    }

    @Test
    fun newProjectsDefaultToTheEndOfNormalAndCanTakeAnOrder() {
        val service = service()
        assertIs<Result.Saved>(service.create(form("delta")))
        assertEquals(listOf("beta", "gamma", "delta"), service.rows().filterNot { it.master }.map { it.slug })

        assertIs<Result.Saved>(service.create(form("epsilon", order = "1")))
        assertEquals(listOf("epsilon", "beta", "gamma", "delta"), service.rows().filterNot { it.master }.map { it.slug })
    }

    @Test
    fun changingPlacementKeepsContentAndHiddenFields() {
        val service = service()
        val edit = requireNotNull(service.find("alpha")).copy(master = false)
        val saved = assertIs<Result.Saved>(service.update("alpha", edit))
        assertEquals(false, saved.master)
        assertEquals(listOf("alpha", "beta", "gamma"), service.rows().filterNot { it.master }.map { it.slug }.sorted())
        assertEquals(0, service.rows().count { it.master })
        assertNotNull(service.find("alpha"))
        val json = service.javaClass.getDeclaredField("content").also { it.isAccessible = true }.get(service) as AdminContentService
        assertTrue(json.loadContent().first { it.section.id == "personal-projects" }.json.contains("1K users"))
    }

    @Test
    fun editingNeverChangesTheSlug() {
        val service = service()
        val result = service.update("beta", requireNotNull(service.find("beta")).copy(slug = "hijacked", name = "Beta 2"))
        assertIs<Result.Saved>(result)
        assertEquals("Beta 2", service.find("beta")?.name)
        assertEquals(null, service.find("hijacked"))
    }

    @Test
    fun movesWithinItsSectionAndStopsAtTheEnds() {
        val service = service()
        assertIs<Result.Saved>(service.move("gamma", up = true))
        assertEquals(listOf("gamma", "beta"), service.rows().filter { !it.master }.map { it.slug })
        service.move("gamma", up = true)
        assertEquals(listOf("gamma", "beta"), service.rows().filter { !it.master }.map { it.slug })
        assertEquals(listOf("alpha"), service.rows().filter { it.master }.map { it.slug })
    }

    @Test
    fun deletesAProjectAndReportsUnknownSlugs() {
        val service = service()
        assertIs<Result.Saved>(service.delete("beta"))
        assertEquals(listOf("alpha", "gamma"), service.rows().map { it.slug })
        assertIs<Result.Failed>(service.delete("beta"))
    }
}
