package com.personalwebsite.domain.entities

/**
 * Domain entity representing a project
 * Follows Single Responsibility Principle - only contains project data
 */
data class Project(
    val name: String,
    val description: String,
    val techStack: String
)
