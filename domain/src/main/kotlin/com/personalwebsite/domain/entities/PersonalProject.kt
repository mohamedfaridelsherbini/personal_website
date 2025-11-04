package com.personalwebsite.domain.entities

/**
 * Personal project data class
 */
data class PersonalProject(
    val name: String,
    val slug: String,
    val description: String,
    val techStack: String,
    val category: String,
    val summary: String = description,
    val role: String? = null,
    val timeline: String? = null,
    val highlights: List<String> = emptyList(),
    val metrics: List<String> = emptyList(),
    val links: List<ProjectLink> = emptyList()
)

data class ProjectLink(
    val label: String,
    val url: String
)
