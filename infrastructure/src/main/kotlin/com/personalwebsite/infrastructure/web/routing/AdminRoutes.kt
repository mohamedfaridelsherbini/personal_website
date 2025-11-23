package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.infrastructure.admin.AdminContentService
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
import io.ktor.utils.io.core.readBytes
import kotlinx.html.ButtonType
import kotlinx.html.FormEncType
import kotlinx.html.FormMethod
import kotlinx.html.InputType
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.div
import kotlinx.html.form
import kotlinx.html.htmlFor
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.html
import kotlinx.html.id
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.link
import kotlinx.html.meta
import kotlinx.html.p
import kotlinx.html.style
import kotlinx.html.textArea
import kotlinx.html.title
import kotlinx.html.classes
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

fun Routing.adminRoutes(
    adminContentService: AdminContentService,
    authEnabled: Boolean,
) {
    val adminRoutes: Route.() -> Unit = {
        get("/admin") {
            val message = call.request.queryParameters["message"]
            val error = call.request.queryParameters["error"]
            val content = adminContentService.loadContent()

            call.respondHtml(HttpStatusCode.OK) {
                head {
                    title("Admin panel")
                    meta { charset = "UTF-8" }
                    link(rel = "icon", href = "/static/images/favicon-portrait.png", type = "image/png")
                    style {
                        +"""
                        |body { font-family: 'Inter', system-ui, -apple-system, sans-serif; background: #0a0c10; color: #e4ecff; margin: 0; padding: 24px; }
                        |.layout { max-width: 1200px; margin: 0 auto; display: grid; gap: 20px; }
                        |.card { background: rgba(255,255,255,0.05); border: 1px solid rgba(255,255,255,0.08); border-radius: 12px; padding: 20px; box-shadow: 0 10px 40px rgba(0,0,0,0.35); }
                        |h1, h2 { margin: 0 0 12px 0; letter-spacing: 0.3px; }
                        |textarea { width: 100%; min-height: 220px; background: rgba(0,0,0,0.35); color: #e4ecff; border: 1px solid rgba(255,255,255,0.12); border-radius: 10px; padding: 12px; font-family: 'JetBrains Mono', 'SFMono-Regular', ui-monospace, monospace; font-size: 14px; }
                        |label { display: block; margin-bottom: 8px; font-weight: 600; }
                        |button { background: linear-gradient(135deg, #00e0ff, #7c3aed); color: #0a0c10; border: none; border-radius: 10px; padding: 12px 16px; font-weight: 700; cursor: pointer; box-shadow: 0 12px 30px rgba(124, 58, 237, 0.35); }
                        |button:hover { transform: translateY(-1px); transition: 150ms ease-in-out; }
                        |.status { padding: 12px 14px; border-radius: 10px; font-weight: 600; }
                        |.status.success { background: rgba(16, 185, 129, 0.16); border: 1px solid rgba(16, 185, 129, 0.35); color: #bbf7d0; }
                        |.status.error { background: rgba(248, 113, 113, 0.16); border: 1px solid rgba(248, 113, 113, 0.35); color: #fecdd3; }
                        |.section-meta { display: flex; gap: 8px; font-size: 12px; opacity: 0.8; margin-top: 6px; }
                        |.upload-input { display: block; margin: 10px 0; color: #e4ecff; }
                        |.header { display: flex; align-items: center; justify-content: space-between; gap: 12px; flex-wrap: wrap; }
                        """.trimMargin()
                    }
                }
                body {
                    div("layout") {
                        div("header") {
                            h1 { +"Admin panel" }
                            if (authEnabled) {
                                p { +"Protected with HTTP Basic auth (ADMIN_USER/ADMIN_PASSWORD)." }
                            } else {
                                p { +"Warning: ADMIN_USER/ADMIN_PASSWORD not set. Admin panel is not authenticated." }
                            }
                        }

                        if (!message.isNullOrBlank()) {
                            div("status success") { +message }
                        }
                        if (!error.isNullOrBlank()) {
                            div("status error") { +error }
                        }

                        content.forEach { payload ->
                            div("card") {
                                h2 { +payload.section.label }
                                p(classes = "section-meta") {
                                    +"${payload.section.fileName} - ${payload.section.path}"
                                }
                                form {
                                    action = "/admin/content/${payload.section.id}"
                                    method = FormMethod.post
                                    textArea {
                                        name = "json"
                                        +payload.json
                                    }
                                    div {
                                        style = "margin-top: 12px;"
                                        button {
                                            type = kotlinx.html.ButtonType.submit
                                            +"Save ${payload.section.label}"
                                        }
                                    }
                                }
                            }
                        }

                        div("card") {
                            h2 { +"Upload resume" }
                            p(classes = "section-meta") {
                                +"Replaces ${adminContentService.resumeTargetPath()}"
                            }
                            form {
                                action = "/admin/resume"
                                method = FormMethod.post
                                encType = FormEncType.multipartFormData

                                label {
                                    htmlFor = "resume"
                                    +"PDF only"
                                }
                                input {
                                    id = "resume"
                                    type = kotlinx.html.InputType.file
                                    name = "resume"
                                    classes = setOf("upload-input")
                                }
                                button {
                                    type = kotlinx.html.ButtonType.submit
                                    +"Upload resume"
                                }
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

    if (authEnabled) {
        authenticate("adminAuth") {
            adminRoutes()
        }
    } else {
        adminRoutes()
    }
}

private fun encode(text: String): String = URLEncoder.encode(text, StandardCharsets.UTF_8)
