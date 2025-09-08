package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.repositories.PersonalProjectRepository

/**
 * Personal projects repository - returns hardcoded project data
 * TODO: Maybe add project links and screenshots later
 */
class PersonalProjectRepositoryImpl : PersonalProjectRepository {
    
    override suspend fun getPersonalProjects(): List<PersonalProject> {
        // Just hardcoded for now - keeping it simple
        return listOf(
            PersonalProject(
                name = "GreenHub",
                description = "Social application for plant lovers with community features, plant care tracking, and Firebase integration.",
                techStack = "Kotlin • Firebase • Jetpack • Social Features",
                category = "Social App"
            ),
            PersonalProject(
                name = "FocusMusic",
                description = "Relaxation application with music streaming capabilities and customizable ambient sounds.",
                techStack = "Java • MediaPlayer • Custom UI • Audio Processing",
                category = "Music App"
            ),
            PersonalProject(
                name = "Little",
                description = "Educational application for children with interactive learning modules and gamification.",
                techStack = "Kotlin • Custom UI • Educational Content • Gamification",
                category = "Educational App"
            ),
            PersonalProject(
                name = "Eleven",
                description = "Productivity application with task management, time tracking, and goal setting features.",
                techStack = "Kotlin • Room Database • Custom UI • Productivity Tools",
                category = "Productivity App"
            ),
            PersonalProject(
                name = "Custom Calendar Library",
                description = "Reusable Android calendar component library with customizable themes and event management.",
                techStack = "Java • Custom Views • Library Development • UI Components",
                category = "Library"
            ),
            PersonalProject(
                name = "CI/CD Automation Tools",
                description = "Automated testing and build pipelines using Jenkins, Docker, and Python scripts for mobile app deployment.",
                techStack = "Jenkins • Docker • Python • Automation • DevOps",
                category = "DevOps Tools"
            )
        )
    }
}
