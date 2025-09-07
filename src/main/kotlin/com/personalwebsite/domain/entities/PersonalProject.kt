package com.personalwebsite.domain.entities

/**
 * Domain entity representing personal projects
 * Follows Single Responsibility Principle - only contains personal project data
 */
data class PersonalProject(
    val name: String,
    val description: String,
    val techStack: String,
    val category: String
)
