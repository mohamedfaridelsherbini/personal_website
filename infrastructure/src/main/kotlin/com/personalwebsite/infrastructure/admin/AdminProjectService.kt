package com.personalwebsite.infrastructure.admin

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

/**
 * Project collection editing for the admin panel. Works on the raw `personal-projects` JSON so fields the form
 * does not show (for example metrics) are preserved when a project is edited.
 *
 * Placement: "Master" projects are stored with `featured: true` and appear in Big projects; everything else is
 * "Normal" and appears in More projects. Order is the position among projects with the same placement.
 */
class AdminProjectService(
    private val content: AdminContentService,
) {
    data class Row(
        val slug: String,
        val name: String,
        val category: String,
        val master: Boolean,
        val order: Int,
    )

    data class LinkField(val label: String = "", val url: String = "")

    data class Form(
        val name: String = "",
        val slug: String = "",
        val master: Boolean = false,
        val order: String = "",
        val description: String = "",
        val category: String = "",
        val technology: String = "",
        val summary: String = "",
        val role: String = "",
        val timeline: String = "",
        val caseStudy: String = "",
        val links: List<LinkField> = emptyList(),
    )

    sealed interface Result {
        data class Saved(val slug: String, val master: Boolean, val created: Boolean) : Result

        data class Invalid(val errors: Map<String, String>) : Result

        data class Failed(val message: String) : Result
    }

    fun rows(): List<Row> {
        val items = load()
        val counters = mutableMapOf(true to 0, false to 0)
        return items.map { item ->
            val master = item.isMaster()
            val order = (counters[master] ?: 0) + 1
            counters[master] = order
            Row(
                slug = item.text("slug"),
                name = item.text("name"),
                category = item.text("category"),
                master = master,
                order = order,
            )
        }
    }

    fun find(slug: String): Form? {
        val items = load()
        val item = items.firstOrNull { it.text("slug") == slug } ?: return null
        val sameGroup = items.filter { it.isMaster() == item.isMaster() }
        return Form(
            name = item.text("name"),
            slug = item.text("slug"),
            master = item.isMaster(),
            order = (sameGroup.indexOf(item) + 1).toString(),
            description = item.text("description"),
            category = item.text("category"),
            technology = item.text("techStack").split("•").map { it.trim() }.filter { it.isNotEmpty() }.joinToString(", "),
            summary = item.text("summary"),
            role = item.text("role"),
            timeline = item.text("timeline"),
            caseStudy = item.stringList("highlights").joinToString("\n"),
            links =
                item.objectList("links").map { LinkField(it.text("label"), it.text("url")) },
        )
    }

    fun create(form: Form): Result = save(form, existingSlug = null)

    fun update(
        slug: String,
        form: Form,
    ): Result = save(form.copy(slug = slug), existingSlug = slug)

    fun delete(slug: String): Result {
        val items = load().toMutableList()
        val item = items.firstOrNull { it.text("slug") == slug } ?: return Result.Failed("Project not found")
        items.remove(item)
        return persist(items, slug, item.isMaster(), created = false)
    }

    /** Swaps a project with its neighbour in the same placement group; no-op at the ends. */
    fun move(
        slug: String,
        up: Boolean,
    ): Result {
        val items = load().toMutableList()
        val item = items.firstOrNull { it.text("slug") == slug } ?: return Result.Failed("Project not found")
        val group = items.filter { it.isMaster() == item.isMaster() }
        val neighbour = group.getOrNull(group.indexOf(item) + if (up) -1 else 1)
        if (neighbour != null) {
            val a = items.indexOf(item)
            val b = items.indexOf(neighbour)
            items[a] = neighbour
            items[b] = item
        }
        return persist(items, slug, item.isMaster(), created = false)
    }

    private fun persist(
        items: List<JsonObject>,
        slug: String,
        master: Boolean,
        created: Boolean,
    ): Result =
        try {
            val array = JsonArray().also { out -> items.forEach { out.add(it) } }
            content.updateContent(SECTION_ID, array.toString())
            Result.Saved(slug = slug, master = master, created = created)
        } catch (error: Exception) {
            logger.error(error) { "Failed to save project $slug" }
            Result.Failed(error.message ?: "Changes were not saved")
        }

    private fun save(
        form: Form,
        existingSlug: String?,
    ): Result {
        val items = load().toMutableList()
        val existing = existingSlug?.let { slug -> items.firstOrNull { it.text("slug") == slug } }
        if (existingSlug != null && existing == null) return Result.Failed("Project not found")

        val errors = validate(form, items, existingSlug)
        if (errors.isNotEmpty()) return Result.Invalid(errors)

        val order = form.order.trim().toIntOrNull() ?: 0
        val item = existing ?: JsonObject()
        val oldIndex = items.indexOf(existing)
        val wasMaster = existing?.isMaster()
        apply(item, form)

        val placementUnchanged = existing != null && wasMaster == form.master
        if (!(placementUnchanged && order == 0)) {
            items.remove(item)
            val group = items.withIndex().filter { it.value.isMaster() == form.master }.map { it.index }
            val position = if (order > 0) minOf(order - 1, group.size) else group.size
            val insertAt =
                when {
                    position < group.size -> group[position]
                    group.isEmpty() -> items.size
                    else -> group.last() + 1
                }
            items.add(insertAt, item)
        } else if (oldIndex < 0) {
            items.add(item)
        }

        return persist(items, form.slug, form.master, created = existing == null)
    }

    private fun validate(
        form: Form,
        items: List<JsonObject>,
        existingSlug: String?,
    ): Map<String, String> {
        val errors = linkedMapOf<String, String>()
        if (form.name.isBlank()) errors["name"] = "Name is required."
        if (existingSlug == null) {
            when {
                form.slug.isBlank() -> errors["slug"] = "Slug is required."
                !SLUG.matches(form.slug) -> errors["slug"] = "Use lowercase letters, numbers and single hyphens."
                items.any { it.text("slug") == form.slug } -> errors["slug"] = "This slug is already in use."
            }
        }
        if (form.description.isBlank()) errors["description"] = "Short description is required."
        if (form.order.isNotBlank() && form.order.trim().toIntOrNull()?.takeIf { it >= 0 } == null) {
            errors["order"] = "Use a nonnegative whole number."
        }
        form.links.forEachIndexed { index, link ->
            if (link.url.isNotBlank() && !link.url.trim().matches(SAFE_URL)) {
                errors["link$index"] = "Use a link that starts with http:// or https://."
            }
        }
        return errors
    }

    private fun apply(
        item: JsonObject,
        form: Form,
    ) {
        item.addProperty("name", form.name.trim())
        item.addProperty("slug", form.slug.trim())
        item.addProperty("description", form.description.trim())
        item.addProperty(
            "techStack",
            form.technology.split(",", "•").map { it.trim() }.filter { it.isNotEmpty() }.joinToString(" • "),
        )
        item.addProperty("category", form.category.trim().ifEmpty { "Other" })
        item.setOptional("summary", form.summary)
        item.setOptional("role", form.role)
        item.setOptional("timeline", form.timeline)
        val highlights = form.caseStudy.lines().map { it.trim() }.filter { it.isNotEmpty() }
        if (highlights.isEmpty()) {
            item.remove("highlights")
        } else {
            item.add("highlights", JsonArray().also { array -> highlights.forEach { array.add(it) } })
        }
        val links = form.links.filter { it.url.isNotBlank() }
        if (links.isEmpty()) {
            item.remove("links")
        } else {
            item.add(
                "links",
                JsonArray().also { array ->
                    links.forEach { link ->
                        array.add(
                            JsonObject().apply {
                                addProperty("label", link.label.trim().ifEmpty { link.url.trim() })
                                addProperty("url", link.url.trim())
                            },
                        )
                    }
                },
            )
        }
        if (form.master) item.addProperty("featured", true) else item.remove("featured")
    }

    private fun load(): List<JsonObject> {
        val json = content.loadContent().first { it.section.id == SECTION_ID }.json
        return JsonParser.parseString(json).asJsonArray.map { it.asJsonObject }
    }

    private fun JsonObject.isMaster(): Boolean = get("featured")?.takeIf { it.isJsonPrimitive }?.asBoolean == true

    private fun JsonObject.text(name: String): String = get(name)?.takeIf { it.isJsonPrimitive }?.asString.orEmpty()

    private fun JsonObject.stringList(name: String): List<String> =
        get(name)?.takeIf { it.isJsonArray }?.asJsonArray?.map(JsonElement::getAsString).orEmpty()

    private fun JsonObject.objectList(name: String): List<JsonObject> =
        get(name)?.takeIf { it.isJsonArray }?.asJsonArray?.map { it.asJsonObject }.orEmpty()

    private fun JsonObject.setOptional(
        name: String,
        value: String,
    ) {
        if (value.isBlank()) remove(name) else addProperty(name, value.trim())
    }

    companion object {
        private const val SECTION_ID = "personal-projects"
        private val SLUG = Regex("[a-z0-9]+(-[a-z0-9]+)*")
        private val SAFE_URL = Regex("https?://\\S+", RegexOption.IGNORE_CASE)
    }
}
