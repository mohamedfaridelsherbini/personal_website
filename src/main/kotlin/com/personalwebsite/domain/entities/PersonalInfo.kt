package com.personalwebsite.domain.entities

/**
 * Domain entity representing personal information
 * Follows Single Responsibility Principle - only contains personal info data
 */
data class PersonalInfo(
    val name: String,
    val title: String,
    val summary: String,
    val location: String,
    val phone: String,
    val email: String,
    val linkedin: String,
    val github: String
)
