package com.personalwebsite.domain.entities

/**
 * Domain entity representing work experience
 * Follows Single Responsibility Principle - only contains work experience data
 */
data class WorkExperience(
    val company: String,
    val position: String,
    val location: String,
    val period: String,
    val responsibilities: List<String>
)
