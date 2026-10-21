@file:Suppress("ktlint:standard:max-line-length")

package com.personalwebsite.infrastructure.web.routing

import com.personalwebsite.infrastructure.admin.AdminContentService
import com.personalwebsite.infrastructure.admin.AdminProjectService
import com.personalwebsite.infrastructure.admin.AdminSectionService
import kotlinx.html.ButtonType
import kotlinx.html.FlowContent
import kotlinx.html.FormEncType
import kotlinx.html.FormMethod
import kotlinx.html.HTML
import kotlinx.html.InputType
import kotlinx.html.MAIN
import kotlinx.html.a
import kotlinx.html.body
import kotlinx.html.button
import kotlinx.html.classes
import kotlinx.html.details
import kotlinx.html.div
import kotlinx.html.fieldSet
import kotlinx.html.form
import kotlinx.html.h1
import kotlinx.html.h2
import kotlinx.html.head
import kotlinx.html.id
import kotlinx.html.img
import kotlinx.html.input
import kotlinx.html.label
import kotlinx.html.legend
import kotlinx.html.li
import kotlinx.html.link
import kotlinx.html.main
import kotlinx.html.meta
import kotlinx.html.nav
import kotlinx.html.p
import kotlinx.html.script
import kotlinx.html.section
import kotlinx.html.span
import kotlinx.html.strong
import kotlinx.html.summary
import kotlinx.html.textArea
import kotlinx.html.title
import kotlinx.html.ul
import kotlinx.html.unsafe

internal enum class AdminNav { PROJECTS, CONTENT }

internal data class AdminBanner(val kind: Kind, val title: String, val detail: String = "", val links: List<Pair<String, String>> = emptyList()) {
    enum class Kind { SUCCESS, ERROR }
}

internal fun HTML.adminShell(
    pageTitle: String,
    email: String,
    active: AdminNav,
    content: MAIN.() -> Unit,
) {
    head {
        title("$pageTitle · Admin")
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
                nav("admin-nav") {
                    attributes["aria-label"] = "Admin"
                    a(href = "/admin", classes = "admin-nav-link" + if (active == AdminNav.PROJECTS) " is-active" else "") { +"Projects" }
                    a(
                        href = "/admin/content",
                        classes = "admin-nav-link" + if (active == AdminNav.CONTENT) " is-active" else "",
                    ) { +"Content & résumé" }
                }
                div("admin-bar-actions") {
                    span("admin-user") { +email }
                    form(action = "/admin/logout", method = FormMethod.post) {
                        button(type = ButtonType.submit, classes = "ds-button ds-button--outline admin-small") { +"Sign out" }
                    }
                }
            }
        }
        main("admin-main", content)
    }
}

internal fun FlowContent.adminBanner(banner: AdminBanner) {
    val success = banner.kind == AdminBanner.Kind.SUCCESS
    div("admin-status " + if (success) "admin-status--success" else "admin-status--error") {
        attributes["role"] = if (success) "status" else "alert"
        div("admin-status-body") {
            strong { +banner.title }
            if (banner.detail.isNotBlank()) p("admin-status-detail") { +banner.detail }
            if (banner.links.isNotEmpty()) {
                p("admin-status-links") {
                    banner.links.forEachIndexed { index, (label, href) ->
                        if (index > 0) +" · "
                        a(href = href) { +label }
                    }
                }
            }
        }
    }
}

internal fun HTML.projectCollectionPage(
    email: String,
    rows: List<AdminProjectService.Row>,
    banner: AdminBanner?,
) = adminShell("Projects", email, AdminNav.PROJECTS) {
    p("admin-eyebrow") { +"Protected admin · Projects" }
    h1 { +"Manage your work" }
    div("admin-actions") {
        a(href = "/admin/projects/new", classes = "ds-button ds-button--primary") { +"Add project" }
    }
    p("admin-note") { +"Changes are saved on the running server. Commit content changes to keep them across redeploys." }
    banner?.let { adminBanner(it) }
    listOf(true to "Big projects", false to "More projects").forEach { (master, heading) ->
        val group = rows.filter { it.master == master }
        h2("admin-group-title") { +"$heading (${group.size})" }
        ul("admin-rows") {
            group.forEachIndexed { index, row ->
                li("admin-row") {
                    id = "project-${row.slug}"
                    div("admin-row-main") {
                        strong { +row.name }
                        span("admin-row-sub") { +row.category }
                    }
                    div("admin-row-actions") {
                        moveButton(row, "up", "↑", disabled = index == 0)
                        moveButton(row, "down", "↓", disabled = index == group.lastIndex)
                        a(href = "/projects/${row.slug}", classes = "ds-button ds-button--outline admin-small") {
                            attributes["target"] = "_blank"
                            attributes["rel"] = "noopener"
                            attributes["aria-label"] = "View ${row.name}"
                            +"View ↗"
                        }
                        a(href = "/admin/projects/${row.slug}/edit", classes = "ds-button ds-button--outline admin-small") {
                            attributes["aria-label"] = "Edit ${row.name}"
                            +"Edit"
                        }
                    }
                }
            }
        }
    }
}

private fun FlowContent.moveButton(
    row: AdminProjectService.Row,
    dir: String,
    glyph: String,
    disabled: Boolean,
) {
    form(action = "/admin/projects/${row.slug}/move", method = FormMethod.post, classes = "admin-inline-form") {
        input(type = InputType.hidden, name = "dir") { value = dir }
        button(type = ButtonType.submit, classes = "ds-button ds-button--outline admin-small admin-move") {
            attributes["aria-label"] = "Move ${row.name} ${if (dir == "up") "up" else "down"}"
            if (disabled) attributes["disabled"] = "disabled"
            +glyph
        }
    }
}

private fun FlowContent.field(
    id: String,
    labelText: String,
    hint: String,
    error: String?,
    control: FlowContent.() -> Unit,
) {
    div("admin-field" + if (error != null) " has-error" else "") {
        label {
            attributes["for"] = id
            +labelText
        }
        control()
        p("admin-hint" + if (error != null) " admin-hint--error" else "") {
            this.id = "$id-hint"
            +(error ?: hint)
        }
    }
}

private fun FlowContent.textField(
    id: String,
    labelText: String,
    placeholderText: String,
    value: String,
    hint: String,
    error: String?,
    readOnly: Boolean = false,
    required: Boolean = false,
) = field(id, labelText, hint, error) {
    input(type = InputType.text, name = id) {
        this.id = id
        this.value = value
        placeholder = placeholderText
        attributes["aria-describedby"] = "$id-hint"
        if (error != null) attributes["aria-invalid"] = "true"
        if (readOnly) attributes["readonly"] = "readonly"
        if (required) attributes["required"] = "required"
    }
}

private fun FlowContent.areaField(
    id: String,
    labelText: String,
    placeholderText: String,
    value: String,
    hint: String,
    error: String?,
    rows: Int = 4,
) = field(id, labelText, hint, error) {
    textArea(rows = rows.toString()) {
        this.id = id
        name = id
        placeholder = placeholderText
        attributes["aria-describedby"] = "$id-hint"
        if (error != null) attributes["aria-invalid"] = "true"
        +value
    }
}

internal fun HTML.projectFormPage(
    email: String,
    editSlug: String?,
    form: AdminProjectService.Form,
    errors: Map<String, String>,
    failure: String?,
) {
    val editing = editSlug != null
    val heading = if (editing) "Edit ${form.name.ifBlank { editSlug }}" else "Add project"
    adminShell(heading, email, AdminNav.PROJECTS) {
        p("admin-eyebrow") { +"Protected admin · ${if (editing) "Edit project" else "Add project"}" }
        h1 { +heading }
        if (errors.isNotEmpty()) {
            adminBanner(AdminBanner(AdminBanner.Kind.ERROR, "Check the required fields", errors.values.distinct().joinToString(" ")))
        }
        if (failure != null) {
            adminBanner(AdminBanner(AdminBanner.Kind.ERROR, "Changes were not saved", "Your input is retained. $failure"))
        }
        form(action = if (editing) "/admin/projects/$editSlug" else "/admin/projects", method = FormMethod.post, classes = "admin-form") {
            attributes["data-admin-project-form"] = "true"
            div("admin-grid") {
                textField("name", "Name *", "Project name", form.name, "Required", errors["name"], required = true)
                textField(
                    "slug",
                    "Slug *",
                    "stable-project-slug",
                    form.slug,
                    if (editing) "Published URLs stay stable, so the slug cannot change." else "Required; published URLs stay stable.",
                    errors["slug"],
                    readOnly = editing,
                    required = true,
                )
            }
            fieldSet("admin-placement") {
                legend { +"Website placement *" }
                div("admin-placement-options") {
                    listOf(true to "Large showcase in Big projects", false to "Compact card in More projects").forEach { (master, helper) ->
                        label("admin-placement-option") {
                            input(type = InputType.radio, name = "placement") {
                                value = if (master) "master" else "normal"
                                checked = form.master == master
                            }
                            strong { +(if (master) "Master" else "Normal") }
                            span("admin-row-sub") { +helper }
                        }
                    }
                }
                div("admin-preview") {
                    attributes["aria-live"] = "polite"
                    strong { +(if (form.master) "Big projects" else "More projects") }
                    p("admin-hint") { +"Your project appears once here; changing placement preserves its content and URL." }
                }
            }
            textField(
                "order",
                "Order in section",
                "0",
                form.order,
                "Nonnegative integer; 0 keeps the current or last position.",
                errors["order"],
            )
            areaField(
                "description",
                "Short description *",
                "Describe the project briefly.",
                form.description,
                "Required",
                errors["description"],
            )
            div("admin-grid") {
                textField("category", "Category", "Project category", form.category, "Optional", null)
                textField("technology", "Technology", "Kotlin, Compose, Firebase", form.technology, "Optional; separate with commas.", null)
            }
            areaField("summary", "Summary", "Detail introduction", form.summary, "Optional; falls back to short description.", null)
            div("admin-grid") {
                textField("role", "Your role", "Verified contribution only", form.role, "Optional", null)
                textField("timeline", "Timeline", "Verified dates only", form.timeline, "Optional", null)
            }
            areaField(
                "caseStudy",
                "Case-study content",
                "One highlight per line",
                form.caseStudy,
                "Optional; publish only supported claims. One point per line.",
                null,
                rows = 6,
            )
            div("admin-links") {
                attributes["data-admin-links"] = "true"
                (form.links + List(maxOf(0, 2 - form.links.size)) { AdminProjectService.LinkField() }).forEachIndexed { index, link ->
                    div("admin-grid admin-link-row") {
                        textField("linkLabel$index", "Link label", "Live site, source or another destination", link.label, "Optional", null)
                        textField(
                            "linkUrl$index",
                            "Destination URL",
                            "https://…",
                            link.url,
                            "Supported schemes and safe links only.",
                            errors["link$index"],
                        )
                    }
                }
                button(type = ButtonType.button, classes = "ds-button ds-button--outline admin-small") {
                    attributes["data-add-link"] = "true"
                    +"Add another link"
                }
            }
            div("admin-form-actions") {
                button(type = ButtonType.submit, classes = "ds-button ds-button--primary") {
                    attributes["data-submit"] = "true"
                    +(if (editing) "Save changes" else "Add project")
                }
                a(href = "/admin", classes = "admin-cancel") { +"Cancel" }
            }
        }
        if (editing) {
            form(action = "/admin/projects/$editSlug/delete", method = FormMethod.post, classes = "admin-delete-form") {
                attributes["onsubmit"] = "return confirm('Delete this project? This cannot be undone.');"
                button(type = ButtonType.submit, classes = "ds-button ds-button--outline admin-small admin-danger") { +"Delete project" }
            }
        }
        script {
            unsafe { raw(PROJECT_FORM_SCRIPT) }
        }
    }
}

private val PROJECT_FORM_SCRIPT =
    """
    (function () {
      var form = document.querySelector('[data-admin-project-form]');
      if (!form) return;
      var preview = form.querySelector('.admin-preview strong');
      form.querySelectorAll('input[name="placement"]').forEach(function (radio) {
        radio.addEventListener('change', function () {
          preview.textContent = radio.value === 'master' ? 'Big projects' : 'More projects';
        });
      });
      var links = form.querySelector('[data-admin-links]');
      var add = form.querySelector('[data-add-link]');
      add.addEventListener('click', function () {
        var rows = links.querySelectorAll('.admin-link-row');
        var next = rows.length;
        var copy = rows[rows.length - 1].cloneNode(true);
        copy.querySelectorAll('input').forEach(function (input) {
          input.value = '';
          input.id = input.id.replace(/\d+${'$'}/, next);
          input.name = input.name.replace(/\d+${'$'}/, next);
        });
        copy.querySelectorAll('label').forEach(function (l) { l.setAttribute('for', l.getAttribute('for').replace(/\d+${'$'}/, next)); });
        links.insertBefore(copy, add);
      });
      form.addEventListener('submit', function () {
        var submit = form.querySelector('[data-submit]');
        submit.disabled = true;
        submit.textContent = 'Saving changes…';
      });
    })();
    """.trimIndent()

private fun FlowContent.sectionFields(
    sectionId: String,
    prefix: String,
    fields: List<AdminSectionService.Field>,
    values: Map<String, String>,
) {
    fields.forEach { field ->
        val fieldId = "$sectionId-$prefix-${field.key}"
        div("admin-field") {
            label {
                attributes["for"] = fieldId
                +(field.label + if (field.required) " *" else "")
            }
            if (field.kind == AdminSectionService.Kind.TEXT) {
                input(type = InputType.text, name = "f_${field.key}") {
                    id = fieldId
                    value = values[field.key].orEmpty()
                    if (field.required) attributes["required"] = "required"
                }
            } else {
                textArea(rows = if (field.kind == AdminSectionService.Kind.LINES) "5" else "3") {
                    id = fieldId
                    name = "f_${field.key}"
                    +values[field.key].orEmpty()
                }
            }
        }
    }
}

internal fun HTML.contentPage(
    email: String,
    sections: List<AdminSectionService.SectionView>,
    resumePath: String,
    banner: AdminBanner?,
) = adminShell("Content & résumé", email, AdminNav.CONTENT) {
    p("admin-eyebrow") { +"Protected admin · Content" }
    h1 { +"Content & résumé" }
    p("admin-intro") { +"Add, edit or delete items. Projects are managed on the Projects tab." }
    banner?.let { adminBanner(it) }
    sections.forEach { view ->
        val schema = view.schema
        section("admin-card") {
            id = "section-${schema.id}"
            h2 { +schema.label }
            if (schema.single) {
                val entry = view.entries.first()
                form(action = "/admin/content/${schema.id}/0/update", method = FormMethod.post, classes = "admin-form") {
                    sectionFields(schema.id, "0", schema.fields, entry.values)
                    button(type = ButtonType.submit, classes = "ds-button ds-button--primary") { +"Save ${schema.label.lowercase()}" }
                }
            } else {
                view.entries.forEach { entry ->
                    details("admin-entry") {
                        summary { +entry.title }
                        form(action = "/admin/content/${schema.id}/${entry.index}/update", method = FormMethod.post, classes = "admin-form") {
                            sectionFields(schema.id, entry.index.toString(), schema.fields, entry.values)
                            button(type = ButtonType.submit, classes = "ds-button ds-button--primary admin-small") { +"Save changes" }
                        }
                        form(action = "/admin/content/${schema.id}/${entry.index}/delete", method = FormMethod.post, classes = "admin-delete-form") {
                            attributes["onsubmit"] = "return confirm('Delete this ${schema.itemLabel}? This cannot be undone.');"
                            button(type = ButtonType.submit, classes = "ds-button ds-button--outline admin-small admin-danger") { +"Delete" }
                        }
                    }
                }
                details("admin-entry admin-entry--new") {
                    summary { +"+ Add ${schema.itemLabel}" }
                    form(action = "/admin/content/${schema.id}/add", method = FormMethod.post, classes = "admin-form") {
                        sectionFields(schema.id, "new", schema.fields, emptyMap())
                        button(type = ButtonType.submit, classes = "ds-button ds-button--primary admin-small") { +"Add ${schema.itemLabel}" }
                    }
                }
            }
        }
    }
    section("admin-card") {
        h2 { +"Upload résumé" }
        p("admin-meta") { +"Replaces $resumePath" }
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
