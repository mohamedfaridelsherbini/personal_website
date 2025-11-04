package tools

import com.personalwebsite.domain.entities.Language
import com.personalwebsite.domain.entities.PersonalInfo
import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.domain.entities.ProjectLink
import com.personalwebsite.domain.entities.Skill
import com.personalwebsite.domain.entities.WorkExperience

val personalInfo = PersonalInfo(
    name = "Mohamed ElSherbini",
    title = "Senior Android Engineer",
    summary = "Senior Android Engineer with 8+ years shipping Kotlin-first experiences across fintech, automation, and productivity products. Obsessed with resilient architecture, automation pipelines, and user journeys that balance compliance with delight.",
    location = "Augsburg, Bavaria, Germany",
    phone = "+49 172 6079066",
    email = "m.farid.shawky@gmail.com",
    linkedin = "https://linkedin.com/in/mofaridelsherbini",
    github = "https://github.com/mohamedfaridelsherbini",
)

val skills = listOf(
    Skill(
        category = "Languages",
        items = listOf(
            "Kotlin",
            "Java",
            "Swift",
            "Python",
            "SQL",
        )
    ),
    Skill(
        category = "Mobile & Architecture",
        items = listOf(
            "Android (Jetpack Compose, MVVM, MVI)",
            "iOS (Xcode, UIKit, SwiftUI)",
            "Kotlin Multiplatform",
            "Clean Architecture Patterns",
        )
    ),
    Skill(
        category = "Testing & Automation",
        items = listOf(
            "Selenium & Appium (Android/iOS)",
            "Espresso & JUnit",
            "Mockito",
            "Automation Framework Design",
        )
    ),
    Skill(
        category = "Backend & DevOps",
        items = listOf(
            "Spring Boot Microservices",
            "REST API Design",
            "PostgreSQL",
            "Docker, Kubernetes, Jenkins, GitHub Actions",
        )
    ),
    Skill(
        category = "Frameworks & SDKs",
        items = listOf(
            "Room, Retrofit, Firebase",
            "Dagger Hilt, Koin, RxJava",
            "Google Maps SDK",
            "ZKteco Biometric SDKs",
        )
    ),
    Skill(
        category = "Tools & Collaboration",
        items = listOf(
            "Android Studio & Gradle",
            "Postman & Charles",
            "Figma & Design Systems",
            "Agile Delivery & Code Review",
        )
    ),
    Skill(
        category = "Other Skills",
        items = listOf(
            "RESTful API Integration",
            "SAP Integration",
            "Unity 3D",
            "Arduino & IoT",
            "Printed Circuit Boards",
        )
    ),
)

val languages = listOf(
    Language(
        name = "Arabic",
        level = "Native"
    ),
    Language(
        name = "English",
        level = "Fluent"
    ),
    Language(
        name = "German",
        level = "A1 (Currently studying A2)"
    ),
)

val workExperience = listOf(
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
            "Collaborated in Agile teams, contributing to sprint planning, retrospectives, and code reviews.",
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
            "Supported DevOps readiness with Docker configurations.",
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
            "Performed secure API integration and optimized performance on low-end devices.",
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
            "Participated in testing workflows and created CI pipelines using Docker locally.",
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
            "Developed Android libraries (custom calendar, fingerprint API wrappers).",
        )
    ),
)

val projects = listOf(
    PersonalProject(
        name = "SPL Fantasy",
        slug = "spl-fantasy",
        description = "Fantasy football platform for the Saudi Pro League with live match data, player stats, and biometric-secured logins.",
        techStack = "Kotlin • MVVM • Fingerprint SDKs • Retrofit",
        category = "Sports / Fantasy",
        role = "Lead Android Engineer",
        timeline = "2018 \u2013 2019",
        highlights = listOf(
            "Designed coroutine-based data ingestion for live match updates under 5 seconds.",
            "Implemented biometric loyalty logins using fingerprint SDKs across 3 partner clubs.",
            "Rolled out feature flags and release trains that kept partners on a weekly cadence.",
        ),
        metrics = listOf(
            "50K+ weekly active managers",
            "99.2% crash-free sessions",
        ),
        links = emptyList()
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
            "Provided real-time insights via custom widgets and admin portal integration.",
        ),
        metrics = listOf(
            "Deployed across 40+ branches",
            "Check-in latency < 3s even on 3G",
        ),
        links = emptyList()
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
            "Experimented with dynamic color modes to match user mood presets.",
        ),
        metrics = listOf(
            "3K monthly active plant keepers",
            "Push open rate consistently >45%",
        ),
        links = emptyList()
    ),
    PersonalProject(
        name = "FocusMusic",
        slug = "focusmusic",
        description = "Meditation and focus music experience with custom soundscapes, offline caching, and adaptive UI themes.",
        techStack = "Kotlin • MediaPlayer • Compose • Audio Streaming",
        category = "Wellness",
        role = null,
        timeline = "2021",
        highlights = listOf(
            "Crafted adaptive ambient engine using Kotlin flows for mood-based playlists.",
            "Added on-device audio analysis to blend binaural beats and white noise.",
            "Launched offline caching with encryption for premium subscribers.",
        ),
        metrics = listOf(
            "Average session length 18 min",
            "Play/pause latency under 200 ms",
        ),
        links = emptyList()
    ),
    PersonalProject(
        name = "Custom Calendar Library",
        slug = "custom-calendar-library",
        description = "Reusable calendar component providing timeline, event grouping, and theming APIs for enterprise apps.",
        techStack = "Kotlin • Custom Views • Library Distribution",
        category = "Library",
        role = null,
        timeline = null,
        highlights = listOf(
            "Supports day, week, timeline, and agenda layouts with drag-to-reschedule.",
            "Theme engine exposed through a Kotlin DSL for brand customization.",
            "Adopted internally by two fintech squads without additional support.",
        ),
        metrics = listOf(
            "Published as private Maven artifact",
            "<2 ms render time per cell on mid-tier devices",
        ),
        links = emptyList()
    ),
    PersonalProject(
        name = "Automation Framework",
        slug = "automation-framework",
        description = "Selenium + Appium automation framework containerized with Docker and orchestrated through Jenkins pipelines.",
        techStack = "Selenium • Appium • Docker • Jenkins • Python",
        category = "Automation / DevOps",
        role = null,
        timeline = "2019 \u2013 Present",
        highlights = listOf(
            "Provisioned ephemeral Docker grids for Android/iOS suites in under 2 minutes.",
            "Unified reporting dashboard with flaky-test quarantine and Slack alerts.",
            "Integrated secrets management and blue/green deploy checks before releases.",
        ),
        metrics = listOf(
            "Regression run time reduced 60%",
            "Automated 350+ test scenarios",
        ),
        links = emptyList()
    ),
    PersonalProject(
        name = "FamilyHub",
        slug = "familyhub",
        description = "Kotlin Multiplatform + Spring Boot microservice app that keeps shared calendars, chores, and budgets synced across family devices.",
        techStack = "Kotlin Multiplatform • SwiftUI • Spring Boot • PostgreSQL",
        category = "Open Source",
        role = null,
        timeline = "2023 \u2013 Present",
        highlights = listOf(
            "Shared domain models compiled for Android, iOS, and desktop clients.",
            "Event reconciliation service built with Spring Boot + Postgres logical decoding.",
            "Experimented with widgets and watch complications for quick capture.",
        ),
        metrics = listOf(
            "Household sync latency < 1s",
            "100% offline coverage for key flows",
        ),
        links = emptyList()
    ),
    PersonalProject(
        name = "CraftMind Agentic AI",
        slug = "craftmind-agentic-ai",
        description = "Local LLM-based MCP implementation tailored for developer automation, task routing, and repo intelligence.",
        techStack = "Kotlin • Python • MCP • Docker • AI Tooling",
        category = "Open Source",
        role = null,
        timeline = "2024 \u2013 Present",
        highlights = listOf(
            "Bridges Kotlin services with local LLMs using the Model Context Protocol.",
            "Automates PR summaries, test suggestions, and release notes via plugins.",
            "Dockerized dev environment for reproducible agent workflows.",
        ),
        metrics = listOf(
            "Automated 40% of release note drafting",
            "Agent tasks execute in <25s on local hardware",
        ),
        links = emptyList()
    ),
    PersonalProject(
        name = "Personal Website",
        slug = "personal-website",
        description = "Public portfolio and blog at mohamedfaridelsherbini.com built with Ktor, Kotlin DSL templates, and neon cyberpunk styling.",
        techStack = "Kotlin • Ktor • Docker • CSS Animations",
        category = "Open Source",
        role = null,
        timeline = null,
        highlights = listOf(
            "Neon glassmorphism design system rendered via server-side Kotlin DSL.",
            "Custom deployment script orchestrating Docker builds on DigitalOcean.",
            "Animated cursor, cluster cards, and cyberpunk typography.",
        ),
        metrics = listOf(
            "Lighthouse performance score 95+",
            "<25 KB critical CSS delivered inline",
        ),
        links = listOf(
            ProjectLink(
                label = "Live Site",
                url = "https://www.mohamedfaridelsherbini.com"
            ),
            ProjectLink(
                label = "GitHub",
                url = "https://github.com/mohamedfaridelsherbini/personal_website"
            ),
        )
    ),
)
