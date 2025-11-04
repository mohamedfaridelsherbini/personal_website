package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.repositories.PersonalProjectRepository

/**
 * Personal projects repository - returns hardcoded project data
 */
class PersonalProjectRepositoryImpl : PersonalProjectRepository {
    
    override suspend fun getPersonalProjects(): List<PersonalProject> {
        // Just hardcoded for now - keeping it simple
        return listOf(
            PersonalProject(
                name = "SPL Fantasy",
                description = "Fantasy football platform for the Saudi Pro League with live match data, player stats, and biometric-secured logins.",
                techStack = "Kotlin • MVVM • Fingerprint SDKs • Retrofit",
                category = "Sports / Fantasy"
            ),
            PersonalProject(
                name = "Fingerprint Attendance",
                description = "Biometric attendance tracking solution integrating ZKteco devices with a Kotlin-based Android client and real-time reporting.",
                techStack = "Kotlin • ZKteco SDK • Room • REST APIs",
                category = "Enterprise"
            ),
            PersonalProject(
                name = "GreenHub",
                description = "Community hub for plant lovers featuring care guides, social sharing, and Firebase-powered notifications.",
                techStack = "Kotlin • Firebase • Jetpack Compose • Social Features",
                category = "Social App"
            ),
            PersonalProject(
                name = "FocusMusic",
                description = "Meditation and focus music experience with custom soundscapes, offline caching, and adaptive UI themes.",
                techStack = "Kotlin • MediaPlayer • Compose • Audio Streaming",
                category = "Wellness"
            ),
            PersonalProject(
                name = "Custom Calendar Library",
                description = "Reusable calendar component providing timeline, event grouping, and theming APIs for enterprise apps.",
                techStack = "Kotlin • Custom Views • Library Distribution",
                category = "Library"
            ),
            PersonalProject(
                name = "Automation Framework",
                description = "Selenium + Appium automation framework containerized with Docker and orchestrated through Jenkins pipelines.",
                techStack = "Selenium • Appium • Docker • Jenkins • Python",
                category = "Automation / DevOps"
            ),
            PersonalProject(
                name = "FamilyHub",
                description = "Kotlin Multiplatform + Spring Boot microservice app that keeps shared calendars, chores, and budgets synced across family devices.",
                techStack = "Kotlin Multiplatform • SwiftUI • Spring Boot • PostgreSQL",
                category = "Open Source"
            ),
            PersonalProject(
                name = "CraftMind Agentic AI",
                description = "Local LLM-based MCP implementation tailored for developer automation, task routing, and repo intelligence.",
                techStack = "Kotlin • Python • MCP • Docker • AI Tooling",
                category = "Open Source"
            ),
            PersonalProject(
                name = "Personal Website",
                description = "Public portfolio and blog at mohamedfaridelsherbini.com built with Ktor, Kotlin DSL templates, and neon cyberpunk styling.",
                techStack = "Kotlin • Ktor • Docker • CSS Animations",
                category = "Open Source"
            )
        )
    }
}
