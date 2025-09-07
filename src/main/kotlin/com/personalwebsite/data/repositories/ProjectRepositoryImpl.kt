package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.Project
import com.personalwebsite.domain.repositories.ProjectRepository

/**
 * Concrete implementation of ProjectRepository
 * Follows Dependency Inversion Principle - implements the abstraction
 * Follows Single Responsibility Principle - only handles project data
 */
class ProjectRepositoryImpl : ProjectRepository {
    
    override suspend fun getProjects(): List<Project> {
        // In a real application, this would fetch from a database or API
        return listOf(
            Project(
                name = "Check24 Loan Comparison Platform",
                description = "Enterprise Android application for loan comparison with multi-module architecture, MVI pattern, and comprehensive testing.",
                techStack = "Kotlin • MVI • Dagger Hilt • Jetpack • Spring Boot"
            ),
            Project(
                name = "Evntoo Event Management Platform",
                description = "Dual-platform event management system with user/provider apps, payment integration, and real-time features.",
                techStack = "Kotlin • MVVM • Room • RxKotlin • Firebase • Google Maps"
            ),
            Project(
                name = "mRAK Government Services App",
                description = "Government services application with SAP integration, QR code functionality, and secure data handling.",
                techStack = "Kotlin • MVI • Coroutines • SAP Integration • Room"
            ),
            Project(
                name = "Fish Market Management System",
                description = "Complete market management solution with live chat, payment processing, and vendor management.",
                techStack = "Kotlin • Firebase • Custom Chat • Payment Gateway"
            ),
            Project(
                name = "HR & Attendance Management Suite",
                description = "Comprehensive HR solution with biometric attendance, ERP integration, and reporting dashboard.",
                techStack = "Java • Kotlin • Fingerprint SDK • ERP Integration • Retrofit"
            ),
            Project(
                name = "Fantasy Football Platform",
                description = "Multi-platform fantasy sports application with real-time scoring, league management, and social features.",
                techStack = "Kotlin • MVVM • ZKteco SDK • Real-time Updates"
            )
        )
    }
}
