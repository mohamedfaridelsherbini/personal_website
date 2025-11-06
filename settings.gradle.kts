pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "personal-website"

include(
    "domain",
    "application",
    "infrastructure",
    "bootstrap"
)
