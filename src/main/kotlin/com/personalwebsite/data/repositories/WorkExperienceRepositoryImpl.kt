package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.WorkExperience
import com.personalwebsite.domain.repositories.WorkExperienceRepository

/**
 * Work experience repository - just returns hardcoded data
 */
class WorkExperienceRepositoryImpl : WorkExperienceRepository {
    
    override suspend fun getWorkExperience(): List<WorkExperience> {
        // Just hardcoded for now - keeping it simple
        return listOf(
            WorkExperience(
                company = "Check24 Vergleich GmbH",
                position = "Senior Android Engineer",
                location = "Munich, Germany",
                period = "Sep 2022 – Present",
                responsibilities = listOf(
                    "Led the development of Android loan comparison features using Kotlin, MVI, Dagger Hilt, and Jetpack.",
                    "Refactored legacy codebase into a scalable multi-module architecture with full test coverage.",
                    "Built integration layers with backend microservices using Spring Boot and PostgreSQL.",
                    "Designed and implemented automation project for Android & iOS apps using Selenium and Appium.",
                    "Extended iOS development skills with Swift (Xcode, UIKit, SwiftUI) for feature parity and cross-team collaboration.",
                    "Maintained CI/CD pipelines with Docker and Kubernetes for consistent build environments.",
                    "Collaborated in Agile teams, contributing to sprint planning, retrospectives, and code reviews."
                )
            ),
            WorkExperience(
                company = "Evntoo",
                position = "Senior Android Engineer",
                location = "Cairo, Egypt",
                period = "Dec 2020 – Sep 2022",
                responsibilities = listOf(
                    "Developed Evntoo User & Provider apps using Kotlin, MVVM, Room, RxKotlin, Firebase.",
                    "Implemented payment gateways, encrypted storage, Google Maps, and location services.",
                    "Added full test coverage and continuous deployment via Jenkins.",
                    "Supported DevOps readiness with Docker configurations."
                )
            ),
            WorkExperience(
                company = "Julfar Consultancy Services",
                position = "Senior Android Engineer",
                location = "Cairo, Egypt",
                period = "Feb 2019 – Dec 2020",
                responsibilities = listOf(
                    "Developed mRAK Government app and Fish Market apps using Kotlin, Room, SAP integrations.",
                    "Used MVI pattern, Coroutines, Firebase, and custom QR/Live chat integrations.",
                    "Wrote and maintained Unit and UI tests using JUnit and Espresso.",
                    "Performed secure API integration and optimized performance on low-end devices."
                )
            ),
            WorkExperience(
                company = "Felucca",
                position = "Co-Founder / Part-Time Android Engineer",
                location = "Cairo, Egypt",
                period = "Jan 2017 – Mar 2023",
                responsibilities = listOf(
                    "Created apps like GreenHub (plant community), FocusMusic, Little, Eleven.",
                    "Built backend integration-ready apps with clean architecture and full Firebase stack.",
                    "Participated in testing workflows and created CI pipelines using Docker locally."
                )
            ),
            WorkExperience(
                company = "Various Companies (Mawared / Xpress Integration / SpotEG / Freelancer)",
                position = "Android Developer",
                location = "Cairo, Egypt",
                period = "2014 – 2019",
                responsibilities = listOf(
                    "Delivered 15+ apps, including HR systems, attendance apps, and fantasy football platforms.",
                    "Technologies: Java, Kotlin, MVVM, MVP, Retrofit, Fingerprint SDKs.",
                    "Built ERP tools with POS printer integration, map APIs, and testing modules.",
                    "Developed Android libraries (custom calendar, fingerprint API wrappers)."
                )
            )
        )
    }
}
