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
                category = "Mobile & Architecture",
                items = listOf(
                    "Android (Jetpack Compose, MVVM, MVI)",
                    "iOS (Xcode, UIKit, SwiftUI)",
                    "Kotlin Multiplatform",
                    "Clean Architecture Patterns"
                )
            ),
            Skill(
                category = "Testing & Automation",
                items = listOf(
                    "Selenium & Appium (Android/iOS)",
                    "Espresso & JUnit",
                    "Mockito",
                    "Automation Framework Design"
                )
            ),
            Skill(
                category = "Backend & DevOps",
                items = listOf(
                    "Spring Boot Microservices",
                    "REST API Design",
                    "PostgreSQL",
                    "Docker, Kubernetes, Jenkins, GitHub Actions"
                )
            ),
            Skill(
                category = "Frameworks & SDKs",
                items = listOf(
                    "Room, Retrofit, Firebase",
                    "Dagger Hilt, Koin, RxJava",
                    "Google Maps SDK",
                    "ZKteco Biometric SDKs"
                )
            ),
            Skill(
                category = "Tools & Collaboration",
                items = listOf(
                    "Android Studio & Gradle",
                    "Postman & Charles",
                    "Figma & Design Systems",
                    "Agile Delivery & Code Review"
                )
            ),
            Skill(
                category = "Other Skills",
                items = listOf(
                    "RESTful API Integration",
                    "SAP Integration",
                    "Unity 3D",
                    "Arduino & IoT",
                    "Printed Circuit Boards"
                )
            )
        )
    }
}
