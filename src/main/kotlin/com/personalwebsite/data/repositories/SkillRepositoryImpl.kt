package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.Skill
import com.personalwebsite.domain.repositories.SkillRepository

/**
 * Concrete implementation of SkillRepository
 * Follows Dependency Inversion Principle - implements the abstraction
 * Follows Single Responsibility Principle - only handles skill data
 */
class SkillRepositoryImpl : SkillRepository {
    
    override suspend fun getSkills(): List<Skill> {
        // In a real application, this would fetch from a database or API
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
