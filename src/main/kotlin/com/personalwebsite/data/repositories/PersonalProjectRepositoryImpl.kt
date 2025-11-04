package com.personalwebsite.data.repositories

import com.personalwebsite.data.content.ContentLoader
import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.entities.ProjectLink
import com.personalwebsite.domain.repositories.PersonalProjectRepository

/**
 * Personal projects repository backed by structured content files.
 */
class PersonalProjectRepositoryImpl(
    private val contentLoader: ContentLoader
) : PersonalProjectRepository {

    override suspend fun getPersonalProjects(): List<PersonalProject> {
        val content: List<PersonalProjectContent> = contentLoader.load("content/personal-projects.json")
        return content.map { it.toDomain() }
    }

    private data class PersonalProjectContent(
        val name: String,
        val slug: String,
        val description: String,
        val techStack: String,
        val category: String,
        val summary: String? = null,
        val role: String? = null,
        val timeline: String? = null,
        val highlights: List<String>? = null,
        val metrics: List<String>? = null,
        val links: List<ProjectLink>? = null
    ) {
        fun toDomain(): PersonalProject = PersonalProject(
            name = name,
            slug = slug,
            description = description,
            techStack = techStack,
            category = category,
            summary = summary ?: description,
            role = role,
            timeline = timeline,
            highlights = highlights ?: emptyList(),
            metrics = metrics ?: emptyList(),
            links = links ?: emptyList()
        )
    }
}
