package com.personalwebsite

import com.personalwebsite.di.appModule
import com.personalwebsite.presentation.controllers.WebsiteController
import com.personalwebsite.presentation.routes.registerRoutes
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger
import org.koin.ktor.ext.inject

/**
 * Main application entry point
 * Started with a simple setup, then added Koin for DI to reduce boilerplate
 */
fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // Install Koin for dependency injection (learned this from a tutorial)
    install(Koin) {
        slf4jLogger()
        modules(appModule)
    }
    
    // Get controller from Koin - much cleaner than manual DI
    val websiteController by inject<WebsiteController>()
    
    registerRoutes(websiteController)
}
