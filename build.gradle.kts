plugins {
    kotlin("jvm") version "1.9.21"
    application
    id("io.ktor.plugin") version "2.3.7"
}

group = "com.personalwebsite"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Ktor core
    implementation("io.ktor:ktor-server-core-jvm")
    implementation("io.ktor:ktor-server-netty-jvm")
    implementation("io.ktor:ktor-server-html-builder-jvm")
    implementation("io.ktor:ktor-server-content-negotiation-jvm")
    implementation("io.ktor:ktor-serialization-gson-jvm")
    implementation("io.ktor:ktor-server-call-logging-jvm")
    
    // HTML DSL
    implementation("org.jetbrains.kotlinx:kotlinx-html-jvm:0.9.1")
    
    // Dependency Injection - Koin (reduces boilerplate significantly)
    implementation("io.insert-koin:koin-ktor:3.5.0")
    implementation("io.insert-koin:koin-logger-slf4j:3.5.0")
    
    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.14")
    implementation("io.github.microutils:kotlin-logging:3.0.5")
    
    // Configuration
    implementation("com.typesafe:config:1.4.2")
    
    // Testing
    testImplementation(kotlin("test"))
    testImplementation("io.insert-koin:koin-test:3.5.0")
    testImplementation("io.ktor:ktor-server-test-host")
}

application {
    mainClass.set("com.personalwebsite.ApplicationKt")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    // Upgrade JVM toolchain to Java 21 (LTS)
    jvmToolchain(21)
}
