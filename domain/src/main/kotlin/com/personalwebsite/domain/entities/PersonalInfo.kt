package com.personalwebsite.domain.entities

/**
 * Personal info data class
 * Simple data holder for now
 */
data class PersonalInfo(
    val name: String,
    val title: String,
    val summary: String,
    val location: String,
    val phone: String,
    val email: String,
    val linkedin: String,
    val github: String,
)
