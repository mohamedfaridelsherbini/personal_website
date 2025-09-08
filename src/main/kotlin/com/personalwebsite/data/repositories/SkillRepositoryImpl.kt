package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.Skill
import com.personalwebsite.domain.repositories.SkillRepository

/**
 * Skills repository - returns hardcoded skill data
 */
class SkillRepositoryImpl : SkillRepository {
    
    override suspend fun getSkills(): List<Skill> {
        // Just hardcoded for now - keeping it simple
        return listOf(
            Skill(
                category = "Languages",
                items = listOf("Kotlin", "Java", "Swift", "Python", "SQL")
            ),
            Skill(
                category = "Mobile Development",
                items = listOf(
                    "Android (Jetpack Compose, MVVM, MVI)",
                    "iOS (Xcode, UIKit, SwiftUI)",
                    "Kotlin Multiplatform",
                    "Clean Architecture"
                )
            ),
            Skill(
                category = "Testing & Automation",
                items = listOf(
                    "Selenium & Appium",
                    "Espresso & JUnit",
                    "Mockito",
                    "Jenkins CI Integration"
                )
            ),
            Skill(
                category = "Backend & DevOps",
                items = listOf(
                    "Spring Boot",
                    "REST APIs",
                    "PostgreSQL",
                    "Docker & Kubernetes"
                )
            )
        )
    }
}
