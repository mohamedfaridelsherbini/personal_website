package com.personalwebsite.presentation

import com.google.gson.Gson
import com.personalwebsite.data.content.ContentLoader
import com.personalwebsite.data.repositories.LanguageRepositoryImpl
import com.personalwebsite.data.repositories.PersonalInfoRepositoryImpl
import com.personalwebsite.data.repositories.PersonalProjectRepositoryImpl
import com.personalwebsite.data.repositories.SkillRepositoryImpl
import com.personalwebsite.data.repositories.WorkExperienceRepositoryImpl
import com.personalwebsite.domain.usecases.GetLanguagesUseCase
import com.personalwebsite.domain.usecases.GetPersonalInfoUseCase
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import com.personalwebsite.domain.usecases.GetSkillsUseCase
import com.personalwebsite.domain.usecases.GetWorkExperienceUseCase
import com.personalwebsite.presentation.cache.ContentCache
import com.personalwebsite.presentation.controllers.WebsiteController
import com.personalwebsite.presentation.views.HtmlWebsiteView
import com.personalwebsite.presentation.views.WebsiteView
import kotlinx.coroutines.runBlocking
import kotlin.io.path.createDirectories
import kotlin.io.path.exists
import kotlin.io.path.readText
import kotlin.io.path.writeText
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import java.nio.file.Path

class WebsiteViewSnapshotTest {

    private lateinit var websiteView: WebsiteView
    private lateinit var controller: WebsiteController
    private lateinit var contentCache: ContentCache

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
        contentCache = ContentCache()
        controller = WebsiteController(
            getPersonalInfoUseCase = getPersonalInfo,
            getSkillsUseCase = getSkills,
            getWorkExperienceUseCase = getWork,
            getPersonalProjectsUseCase = getProjects,
            getLanguagesUseCase = getLanguages,
            websiteView = websiteView,
            contentCache = contentCache
        )
    }

    @Test
    fun homePageMatchesSnapshot() = runBlocking {
        contentCache.clear()
        val html = controller.loadWebsite()
        assertMatchesSnapshot("home", html)
    }

    @Test
    fun projectPageMatchesSnapshot() = runBlocking {
        contentCache.clear()
        val html = controller.loadProject("spl-fantasy")
        assertMatchesSnapshot("project-spl-fantasy", html)
    }

    private fun assertMatchesSnapshot(name: String, content: String) {
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
