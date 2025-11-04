package com.personalwebsite.data.repositories

import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.entities.ProjectLink
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
                slug = "spl-fantasy",
                description = "Fantasy football platform for the Saudi Pro League with live match data, player stats, and biometric-secured logins.",
                techStack = "Kotlin • MVVM • Fingerprint SDKs • Retrofit",
                category = "Sports / Fantasy",
                role = "Lead Android Engineer",
                timeline = "2018 – 2019",
                highlights = listOf(
                    "Designed coroutine-based data ingestion for live match updates under 5 seconds.",
                    "Implemented biometric loyalty logins using fingerprint SDKs across 3 partner clubs.",
                    "Rolled out feature flags and release trains that kept partners on a weekly cadence."
                ),
                metrics = listOf(
                    "50K+ weekly active managers",
                    "99.2% crash-free sessions"
                )
            ),
            PersonalProject(
                name = "Fingerprint Attendance",
                slug = "fingerprint-attendance",
                description = "Biometric attendance tracking solution integrating ZKteco devices with a Kotlin-based Android client and real-time reporting.",
                techStack = "Kotlin • ZKteco SDK • Room • REST APIs",
                category = "Enterprise",
                role = "Mobile Lead",
                timeline = "2019",
                highlights = listOf(
                    "Reverse engineered ZKteco protocol for offline-first syncing to HR dashboards.",
                    "Built resilient Room caching with conflict resolution for remote branches.",
                    "Provided real-time insights via custom widgets and admin portal integration."
                ),
                metrics = listOf(
                    "Deployed across 40+ branches",
                    "Check-in latency < 3s even on 3G"
                )
            ),
            PersonalProject(
                name = "GreenHub",
                slug = "greenhub",
                description = "Community hub for plant lovers featuring care guides, social sharing, and Firebase-powered notifications.",
                techStack = "Kotlin • Firebase • Jetpack Compose • Social Features",
                category = "Social App",
                role = "Co-founder / Android",
                timeline = "2020",
                highlights = listOf(
                    "Composed an adaptive UI system with plant-care timelines and reminders.",
                    "Implemented Firestore cloud functions for weekly challenges and leaderboards.",
                    "Experimented with dynamic color modes to match user mood presets."
                ),
                metrics = listOf(
                    "3K monthly active plant keepers",
                    "Push open rate consistently >45%"
                )
            ),
            PersonalProject(
                name = "FocusMusic",
                slug = "focusmusic",
                description = "Meditation and focus music experience with custom soundscapes, offline caching, and adaptive UI themes.",
                techStack = "Kotlin • MediaPlayer • Compose • Audio Streaming",
                category = "Wellness",
                timeline = "2021",
                highlights = listOf(
                    "Crafted adaptive ambient engine using Kotlin flows for mood-based playlists.",
                    "Added on-device audio analysis to blend binaural beats and white noise.",
                    "Launched offline caching with encryption for premium subscribers."
                ),
                metrics = listOf(
                    "Average session length 18 min",
                    "Play/pause latency under 200 ms"
                )
            ),
            PersonalProject(
                name = "Custom Calendar Library",
                slug = "custom-calendar-library",
                description = "Reusable calendar component providing timeline, event grouping, and theming APIs for enterprise apps.",
                techStack = "Kotlin • Custom Views • Library Distribution",
                category = "Library",
                highlights = listOf(
                    "Supports day, week, timeline, and agenda layouts with drag-to-reschedule.",
                    "Theme engine exposed through a Kotlin DSL for brand customization.",
                    "Adopted internally by two fintech squads without additional support."
                ),
                metrics = listOf(
                    "Published as private Maven artifact",
                    "<2 ms render time per cell on mid-tier devices"
                )
            ),
            PersonalProject(
                name = "Automation Framework",
                slug = "automation-framework",
                description = "Selenium + Appium automation framework containerized with Docker and orchestrated through Jenkins pipelines.",
                techStack = "Selenium • Appium • Docker • Jenkins • Python",
                category = "Automation / DevOps",
                timeline = "2019 – Present",
                highlights = listOf(
                    "Provisioned ephemeral Docker grids for Android/iOS suites in under 2 minutes.",
                    "Unified reporting dashboard with flaky-test quarantine and Slack alerts.",
                    "Integrated secrets management and blue/green deploy checks before releases."
                ),
                metrics = listOf(
                    "Regression run time reduced 60%",
                    "Automated 350+ test scenarios"
                )
            ),
            PersonalProject(
                name = "FamilyHub",
                slug = "familyhub",
                description = "Kotlin Multiplatform + Spring Boot microservice app that keeps shared calendars, chores, and budgets synced across family devices.",
                techStack = "Kotlin Multiplatform • SwiftUI • Spring Boot • PostgreSQL",
                category = "Open Source",
                timeline = "2023 – Present",
                highlights = listOf(
                    "Shared domain models compiled for Android, iOS, and desktop clients.",
                    "Event reconciliation service built with Spring Boot + Postgres logical decoding.",
                    "Experimented with widgets and watch complications for quick capture."
                ),
                metrics = listOf(
                    "Household sync latency < 1s",
                    "100% offline coverage for key flows"
                )
            ),
            PersonalProject(
                name = "CraftMind Agentic AI",
                slug = "craftmind-agentic-ai",
                description = "Local LLM-based MCP implementation tailored for developer automation, task routing, and repo intelligence.",
                techStack = "Kotlin • Python • MCP • Docker • AI Tooling",
                category = "Open Source",
                timeline = "2024 – Present",
                highlights = listOf(
                    "Bridges Kotlin services with local LLMs using the Model Context Protocol.",
                    "Automates PR summaries, test suggestions, and release notes via plugins.",
                    "Dockerized dev environment for reproducible agent workflows."
                ),
                metrics = listOf(
                    "Automated 40% of release note drafting",
                    "Agent tasks execute in <25s on local hardware"
                )
            ),
            PersonalProject(
                name = "Personal Website",
                slug = "personal-website",
                description = "Public portfolio and blog at mohamedfaridelsherbini.com built with Ktor, Kotlin DSL templates, and neon cyberpunk styling.",
                techStack = "Kotlin • Ktor • Docker • CSS Animations",
                category = "Open Source",
                highlights = listOf(
                    "Neon glassmorphism design system rendered via server-side Kotlin DSL.",
                    "Custom deployment script orchestrating Docker builds on DigitalOcean.",
                    "Animated cursor, cluster cards, and cyberpunk typography."
                ),
                metrics = listOf(
                    "Lighthouse performance score 95+",
                    "<25 KB critical CSS delivered inline"
                ),
                links = listOf(
                    ProjectLink(label = "Live Site", url = "https://www.mohamedfaridelsherbini.com"),
                    ProjectLink(label = "GitHub", url = "https://github.com/mohamedfaridelsherbini/personal_website")
                )
            )
        )
    }
}
