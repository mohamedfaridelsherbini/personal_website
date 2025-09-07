package com.personalwebsite.domain.entities

/**
 * Domain entity representing a skill category
 * Follows Single Responsibility Principle - only contains skill data
 */
data class Skill(
    val category: String,
    val items: List<String>
)
