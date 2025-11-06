package com.personalwebsite.application.website

import com.google.gson.Gson
import com.personalwebsite.application.website.ports.RenderCache
import com.personalwebsite.application.website.ports.WebsiteView
import com.personalwebsite.domain.usecases.GetLanguagesUseCase
import com.personalwebsite.domain.usecases.GetPersonalInfoUseCase
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import com.personalwebsite.domain.usecases.GetSkillsUseCase
import com.personalwebsite.domain.usecases.GetWorkExperienceUseCase
import com.personalwebsite.infrastructure.cache.InMemoryRenderCache
import com.personalwebsite.infrastructure.content.ContentLoader
import com.personalwebsite.infrastructure.content.LanguageRepositoryImpl
import com.personalwebsite.infrastructure.content.PersonalInfoRepositoryImpl
import com.personalwebsite.infrastructure.content.PersonalProjectRepositoryImpl
import com.personalwebsite.infrastructure.content.SkillRepositoryImpl
import com.personalwebsite.infrastructure.content.WorkExperienceRepositoryImpl
import com.personalwebsite.infrastructure.web.view.HtmlWebsiteView
import kotlinx.coroutines.runBlocking
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class WebsiteViewSnapshotTest {
    private lateinit var websiteView: WebsiteView
    private lateinit var renderCache: RenderCache
    private lateinit var websiteQueries: WebsiteQueries

    @BeforeTest
    fun setUp() {
        val gson = Gson()
        val loader = ContentLoader(gson)

        val personalInfoRepository = PersonalInfoRepositoryImpl(loader)
        val skillRepository = SkillRepositoryImpl(loader)
        val workRepository = WorkExperienceRepositoryImpl(loader)
        val projectRepository = PersonalProjectRepositoryImpl(loader)
        val languageRepository = LanguageRepositoryImpl(loader)

        val getPersonalInfo = GetPersonalInfoUseCase(personalInfoRepository)
        val getSkills = GetSkillsUseCase(skillRepository)
        val getWork = GetWorkExperienceUseCase(workRepository)
        val getProjects = GetPersonalProjectsUseCase(projectRepository)
        val getLanguages = GetLanguagesUseCase(languageRepository)

        websiteView = HtmlWebsiteView()
        renderCache = InMemoryRenderCache()
        websiteQueries =
            WebsiteService(
                getPersonalInfoUseCase = getPersonalInfo,
                getSkillsUseCase = getSkills,
                getWorkExperienceUseCase = getWork,
                getPersonalProjectsUseCase = getProjects,
                getLanguagesUseCase = getLanguages,
                websiteView = websiteView,
                renderCache = renderCache,
            )
    }

    @Test
    fun homePageMatchesSnapshot() =
        runBlocking {
            renderCache.clear()
            val html = websiteQueries.renderHome()
            assertMatchesSnapshot("home", html)
        }

    @Test
    fun projectPageMatchesSnapshot() =
        runBlocking {
            renderCache.clear()
            val html = websiteQueries.renderProject("spl-fantasy")
            assertMatchesSnapshot("project-spl-fantasy", html)
        }

    private fun assertMatchesSnapshot(
        name: String,
        content: String,
    ) {
        val snapshotDir = Path.of("src/test/resources/snapshots")
        snapshotDir.createDirectories()
        val snapshotFile = snapshotDir.resolve("$name.html")

        val updateSnapshots = System.getenv("UPDATE_SNAPSHOTS")?.toBoolean() == true

        if (!snapshotFile.exists() || updateSnapshots) {
            snapshotFile.writeText(content)
            if (updateSnapshots) {
                return
            }
            error("Snapshot for '$name' did not exist. Created it, please verify and re-run the tests.")
        }

        val expected = snapshotFile.readText()
        assertEquals(expected.trimEnd(), content.trimEnd(), "Snapshot mismatch for $name")
    }
}
