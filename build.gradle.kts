plugins {
    kotlin("jvm") version "1.9.21" apply false
    id("io.ktor.plugin") version "2.3.7" apply false
    id("com.github.johnrengelman.shadow") version "8.1.1" apply false
    id("org.jlleitschuh.gradle.ktlint") version "12.1.0" apply false
}

group = "com.personalwebsite"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        mavenCentral()
    }
}
