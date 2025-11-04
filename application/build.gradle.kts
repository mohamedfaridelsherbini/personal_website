plugins {
    kotlin("jvm")
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
