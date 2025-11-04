package com.personalwebsite.domain.entities

/**
 * Work experience data class
 */
data class WorkExperience(
    val company: String,
    val position: String,
    val location: String,
    val period: String,
    val responsibilities: List<String>
)
