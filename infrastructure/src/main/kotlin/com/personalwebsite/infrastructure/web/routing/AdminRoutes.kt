package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.infrastructure.admin.AdminContentService
import com.personalwebsite.infrastructure.web.auth.ADMIN_SESSION_AUTH
import com.personalwebsite.infrastructure.web.auth.AdminSession
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.forEachPart
import io.ktor.server.application.call
import io.ktor.server.auth.authenticate
import io.ktor.server.html.respondHtml
import io.ktor.server.request.receiveMultipart
import io.ktor.server.request.receiveParameters
import io.ktor.server.response.respondRedirect
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.sessions.get
import io.ktor.server.sessions.sessions
import io.ktor.utils.io.core.readBytes
import kotlinx.html.ButtonType
import kotlinx.html.FormEncType
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.link
import kotlinx.html.main
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.section
import kotlinx.html.span
import kotlinx.html.textArea
import kotlinx.html.title
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun Routing.adminRoutes(adminContentService: AdminContentService) {
    val adminRoutes: Route.() -> Unit = {
        get("/admin") {
            val message = call.request.queryParameters["message"]
            val error = call.request.queryParameters["error"]
            val content = adminContentService.loadContent()

            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title("Admin panel")
                    meta { charset = "UTF-8" }
                    meta(name = "viewport", content = "width=device-width, initial-scale=1")
                    meta(name = "robots", content = "noindex")
                    link(rel = "icon", href = "/static/images/favicon-portrait.png", type = "image/png")
                    link(rel = "stylesheet", href = "/static/css/style.css")
                }
                body("admin-page") {
                    div("admin-bar") {
                        div("admin-bar-inner") {
                            a(href = "/", classes = "admin-brand") {
                                img(src = "/static/images/logo-badge.svg", alt = "") { classes = setOf("admin-brand-logo") }
                                span { +"Mohamed ElSherbini" }
                            }
                            div("admin-bar-actions") {
                                span("admin-user") { +call.sessions.get<AdminSession>()?.email.orEmpty() }
                                form(action = "/admin/logout", method = FormMethod.post) {
                                    button(type = ButtonType.submit, classes = "ds-button ds-button--outline admin-small") { +"Sign out" }
                                }
                            }
                        }
                    }
                    main("admin-main") {
                        h1 { +"Admin panel" }
                        p("admin-intro") { +"Edit the site content as JSON, or replace the résumé PDF." }

                        if (!message.isNullOrBlank()) {
                            div("admin-status admin-status--success") {
                                attributes["role"] = "status"
                                span("admin-status-label") { +"Saved" }
                                span { +message }
                            }
                        }
                        if (!error.isNullOrBlank()) {
                            div("admin-status admin-status--error") {
                                attributes["role"] = "alert"
                                span("admin-status-label") { +"Error" }
                                span { +error }
                            }
                        }

                        content.forEach { payload ->
                            section("admin-card") {
                                h2 { +payload.section.label }
                                p("admin-meta") { +"${payload.section.fileName} · ${payload.section.path}" }
                                form {
                                    action = "/admin/content/${payload.section.id}"
                                    method = FormMethod.post
                                    textArea {
                                        classes = setOf("admin-textarea")
                                        name = "json"
                                        attributes["aria-label"] = "${payload.section.label} JSON"
                                        +payload.json
                                    }
                                    button(type = ButtonType.submit, classes = "ds-button ds-button--primary") {
                                        +"Save ${payload.section.label}"
                                    }
                                }
                            }
                        }

                        section("admin-card") {
                            h2 { +"Upload résumé" }
                            p("admin-meta") { +"Replaces ${adminContentService.resumeTargetPath()}" }
                            form {
                                action = "/admin/resume"
                                method = FormMethod.post
                                encType = FormEncType.multipartFormData

                                label {
                                    attributes["for"] = "resume"
                                    +"PDF file"
                                }
                                input {
                                    id = "resume"
                                    type = InputType.file
                                    name = "resume"
                                    classes = setOf("admin-file")
                                    attributes["accept"] = "application/pdf"
                                }
                                button(type = ButtonType.submit, classes = "ds-button ds-button--primary") { +"Upload résumé" }
                            }
                        }
                    }
                }
            }
        }

        post("/admin/content/{id}") {
            val id = call.parameters["id"]
            if (id.isNullOrBlank()) {
                call.respondText("Missing content section id", status = HttpStatusCode.BadRequest)
                return@post
            }

            val params = call.receiveParameters()
            val json = params["json"]

            if (json.isNullOrBlank()) {
                call.respondText("JSON payload required", status = HttpStatusCode.BadRequest)
                return@post
            }

            try {
                adminContentService.updateContent(id, json)
                call.respondRedirect("/admin?message=${encode("Updated $id")}")
            } catch (exception: Exception) {
                call.respondRedirect("/admin?error=${encode(exception.message ?: "Failed to update $id")}")
            }
        }

        post("/admin/resume") {
            val multipart = call.receiveMultipart()
            var resumeBytes: ByteArray? = null
            var fileName: String? = null

            multipart.forEachPart { part ->
                when (part) {
                    is io.ktor.http.content.PartData.FileItem -> {
                        fileName = part.originalFileName
                        resumeBytes = part.provider().readBytes()
                    }

                    else -> Unit
                }
                part.dispose()
            }

            if (resumeBytes == null) {
                call.respondText("No file uploaded", status = HttpStatusCode.BadRequest)
                return@post
            }

            try {
                adminContentService.replaceResume(fileName, resumeBytes!!)
                call.respondRedirect("/admin?message=${encode("Resume updated")}")
            } catch (exception: Exception) {
                call.respondRedirect("/admin?error=${encode(exception.message ?: "Failed to upload resume")}")
            }
        }
    }

    authenticate(ADMIN_SESSION_AUTH) {
        adminRoutes()
    }
}

private fun encode(text: String): String = URLEncoder.encode(text, StandardCharsets.UTF_8)
