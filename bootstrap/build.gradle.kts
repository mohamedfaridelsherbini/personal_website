plugins {
    kotlin("jvm")
    id("application")
    id("io.ktor.plugin")
}

dependencies {
    implementation(project(":infrastructure"))
    implementation(project(":application"))
    implementation(project(":domain"))

    implementation(libs.ktor.server.netty)
    implementation(libs.koin.ktor)
    implementation(libs.koin.logger.slf4j)
    implementation(libs.logback.classic)
    implementation("com.typesafe:config:1.4.2")
    implementation(libs.gson)
}

application {
    mainClass.set("com.personalwebsite.ApplicationKt")
}

kotlin {
    jvmToolchain(21)
}

ktor {
    fatJar {
        archiveFileName.set("app-all.jar")
    }
}
