package com.personalwebsite.domain.entities

/**
 * Domain entity representing language proficiency
 * Follows Single Responsibility Principle - only contains language data
 */
data class Language(
    val name: String,
    val level: String
)
