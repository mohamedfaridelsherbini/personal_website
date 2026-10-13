package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.infrastructure.admin.AdminContentService
import com.personalwebsite.infrastructure.admin.AdminProjectService
import com.personalwebsite.infrastructure.web.auth.ADMIN_SESSION_AUTH
import com.personalwebsite.infrastructure.web.auth.AdminSession
import io.ktor.http.HttpStatusCode
import io.ktor.http.Parameters
import io.ktor.http.content.forEachPart
import io.ktor.server.application.ApplicationCall
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
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

private fun ApplicationCall.adminEmail(): String = sessions.get<AdminSession>()?.email.orEmpty()

private fun ApplicationCall.banner(): AdminBanner? {
    val message = request.queryParameters["message"]
    val error = request.queryParameters["error"]
    return when {
        !message.isNullOrBlank() -> AdminBanner(AdminBanner.Kind.SUCCESS, message)
        !error.isNullOrBlank() -> AdminBanner(AdminBanner.Kind.ERROR, "Changes were not saved", error)
        else -> null
    }
}

private fun Parameters.toProjectForm(): AdminProjectService.Form {
    val labels = getAll("linkLabel").orEmpty()
    val urls = getAll("linkUrl").orEmpty()
    val numbered =
        generateSequence(0) { it + 1 }.takeWhile { contains("linkUrl$it") || contains("linkLabel$it") }
            .map { AdminProjectService.LinkField(get("linkLabel$it").orEmpty(), get("linkUrl$it").orEmpty()) }
            .toList()
    val plain = urls.indices.map { AdminProjectService.LinkField(labels.getOrNull(it).orEmpty(), urls[it]) }
    return AdminProjectService.Form(
        name = get("name").orEmpty(),
        slug = get("slug").orEmpty().trim(),
        master = get("placement") == "master",
        order = get("order").orEmpty(),
        description = get("description").orEmpty(),
        category = get("category").orEmpty(),
        technology = get("technology").orEmpty(),
        summary = get("summary").orEmpty(),
        role = get("role").orEmpty(),
        timeline = get("timeline").orEmpty(),
        caseStudy = get("caseStudy").orEmpty(),
        links = numbered.ifEmpty { plain },
    )
}

fun Routing.adminRoutes(
    adminContentService: AdminContentService,
    adminProjectService: AdminProjectService,
) {
    val adminRoutes: Route.() -> Unit = {
        get("/admin") {
            val saved = call.request.queryParameters["saved"]
            val slug = call.request.queryParameters["slug"].orEmpty()
            val master = call.request.queryParameters["placement"] == "master"
            val banner =
                when (saved) {
                    "created" ->
                        AdminBanner(
                            AdminBanner.Kind.SUCCESS,
                            "Project added to ${if (master) "Big projects" else "More projects"}",
                            links = listOf("View project ↗" to "/projects/$slug", "Add another project" to "/admin/projects/new"),
                        )
                    "updated" ->
                        AdminBanner(AdminBanner.Kind.SUCCESS, "Changes saved", links = listOf("View project ↗" to "/projects/$slug"))
                    else -> call.banner()
                }
            call.respondHtml(HttpStatusCode.OK) { projectCollectionPage(call.adminEmail(), adminProjectService.rows(), banner) }
        }

        get("/admin/projects/new") {
            call.respondHtml(HttpStatusCode.OK) {
                projectFormPage(call.adminEmail(), null, AdminProjectService.Form(), emptyMap(), null)
            }
        }

        post("/admin/projects") {
            val form = call.receiveParameters().toProjectForm()
            when (val result = adminProjectService.create(form)) {
                is AdminProjectService.Result.Saved ->
                    call.respondRedirect(
                        "/admin?saved=created&slug=${encode(result.slug)}&placement=${if (result.master) "master" else "normal"}",
                    )
                is AdminProjectService.Result.Invalid ->
                    call.respondHtml(
                        HttpStatusCode.UnprocessableEntity,
                    ) { projectFormPage(call.adminEmail(), null, form, result.errors, null) }
                is AdminProjectService.Result.Failed ->
                    call.respondHtml(
                        HttpStatusCode.InternalServerError,
                    ) { projectFormPage(call.adminEmail(), null, form, emptyMap(), result.message) }
            }
        }

        get("/admin/projects/{slug}/edit") {
            val slug = call.parameters["slug"].orEmpty()
            val form = adminProjectService.find(slug)
            if (form == null) {
                call.respondRedirect("/admin?error=${encode("Project not found")}")
            } else {
                call.respondHtml(HttpStatusCode.OK) { projectFormPage(call.adminEmail(), slug, form, emptyMap(), null) }
            }
        }

        post("/admin/projects/{slug}") {
            val slug = call.parameters["slug"].orEmpty()
            val form = call.receiveParameters().toProjectForm().copy(slug = slug)
            when (val result = adminProjectService.update(slug, form)) {
                is AdminProjectService.Result.Saved ->
                    call.respondRedirect(
                        "/admin?saved=updated&slug=${encode(result.slug)}&placement=${if (result.master) "master" else "normal"}",
                    )
                is AdminProjectService.Result.Invalid ->
                    call.respondHtml(
                        HttpStatusCode.UnprocessableEntity,
                    ) { projectFormPage(call.adminEmail(), slug, form, result.errors, null) }
                is AdminProjectService.Result.Failed ->
                    call.respondHtml(
                        HttpStatusCode.InternalServerError,
                    ) { projectFormPage(call.adminEmail(), slug, form, emptyMap(), result.message) }
            }
        }

        get("/admin/content") {
            call.respondHtml(HttpStatusCode.OK) {
                contentPage(
                    call.adminEmail(),
                    adminContentService.loadContent(),
                    adminContentService.resumeTargetPath().toString(),
                    call.banner(),
                )
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
                call.respondRedirect("/admin/content?message=${encode("Updated $id")}")
            } catch (exception: Exception) {
                call.respondRedirect("/admin/content?error=${encode(exception.message ?: "Failed to update $id")}")
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
                call.respondRedirect("/admin/content?message=${encode("Résumé updated")}")
            } catch (exception: Exception) {
                call.respondRedirect("/admin/content?error=${encode(exception.message ?: "Failed to upload résumé")}")
            }
        }
    }

    authenticate(ADMIN_SESSION_AUTH) {
        adminRoutes()
    }
}

private fun encode(text: String): String = URLEncoder.encode(text, StandardCharsets.UTF_8)
