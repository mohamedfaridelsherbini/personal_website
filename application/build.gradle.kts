plugins {
    kotlin("jvm")
    id("org.jlleitschuh.gradle.ktlint")
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.kotlin.logging)

    testImplementation(kotlin("test"))
    testImplementation(project(":domain"))
}

kotlin {
    jvmToolchain(21)
}

tasks.test {
    useJUnitPlatform()
}
