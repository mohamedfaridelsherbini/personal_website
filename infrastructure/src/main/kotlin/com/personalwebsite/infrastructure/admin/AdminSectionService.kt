package com.personalwebsite.infrastructure.admin

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.JsonParser

/**
 * Add / edit / delete for the simple content sections (skills, experience, education, languages and personal info),
 * so the admin never needs to edit raw JSON. Fields a schema does not list are preserved on update.
 */
class AdminSectionService(
    private val content: AdminContentService,
) {
    enum class Kind { TEXT, TEXTAREA, LINES }

    data class Field(val key: String, val label: String, val kind: Kind = Kind.TEXT, val required: Boolean = false)

    data class Schema(
        val id: String,
        val label: String,
        val itemLabel: String,
        val titleKey: String,
        val fields: List<Field>,
        val single: Boolean = false,
    )

    data class Entry(val index: Int, val title: String, val values: Map<String, String>)

    data class SectionView(val schema: Schema, val entries: List<Entry>)

    sealed interface Result {
        data object Saved : Result

        data class Invalid(val message: String) : Result
    }

    fun view(): List<SectionView> = SCHEMAS.map { schema -> SectionView(schema, entries(schema)) }

    fun add(
        id: String,
        values: Map<String, String>,
    ): Result {
        val schema = collection(id) ?: return Result.Invalid("Unknown section")
        validate(schema, values)?.let { return Result.Invalid(it) }
        val array = loadArray(id)
        array.add(build(schema, JsonObject(), values))
        return save(id, array)
    }

    fun update(
        id: String,
        index: Int,
        values: Map<String, String>,
    ): Result {
        val schema = SCHEMAS.firstOrNull { it.id == id } ?: return Result.Invalid("Unknown section")
        validate(schema, values)?.let { return Result.Invalid(it) }
        if (schema.single) {
            val current =
                JsonParser.parseString(content.loadContent().first { it.section.id == id }.json)
                    .takeIf { it.isJsonObject }?.asJsonObject ?: JsonObject()
            return save(id, build(schema, current, values))
        }
        val array = loadArray(id)
        if (index !in 0 until array.size()) return Result.Invalid("Item not found")
        array.set(index, build(schema, array[index].asJsonObject, values))
        return save(id, array)
    }

    fun delete(
        id: String,
        index: Int,
    ): Result {
        collection(id) ?: return Result.Invalid("Unknown section")
        val array = loadArray(id)
        if (index !in 0 until array.size()) return Result.Invalid("Item not found")
        array.remove(index)
        return save(id, array)
    }

    private fun collection(id: String) = SCHEMAS.firstOrNull { it.id == id && !it.single }

    private fun validate(
        schema: Schema,
        values: Map<String, String>,
    ): String? =
        schema.fields.firstOrNull { it.required && values[it.key].isNullOrBlank() }
            ?.let { "${it.label} is required." }

    private fun build(
        schema: Schema,
        target: JsonObject,
        values: Map<String, String>,
    ): JsonObject {
        schema.fields.forEach { field ->
            val raw = values[field.key].orEmpty()
            when (field.kind) {
                Kind.TEXT, Kind.TEXTAREA -> if (raw.isBlank()) target.remove(field.key) else target.addProperty(field.key, raw.trim())
                Kind.LINES ->
                    target.add(
                        field.key,
                        JsonArray().also { out -> raw.lines().map(String::trim).filter(String::isNotEmpty).forEach(out::add) },
                    )
            }
        }
        return target
    }

    private fun entries(schema: Schema): List<Entry> {
        val json = content.loadContent().first { it.section.id == schema.id }.json
        val element = JsonParser.parseString(json)
        val objects = if (schema.single) listOf(element.takeIf { it.isJsonObject }?.asJsonObject ?: JsonObject()) else element.asJsonArray.map { it.asJsonObject }
        return objects.mapIndexed { index, obj ->
            Entry(index, obj.text(schema.titleKey).ifBlank { "Item ${index + 1}" }, schema.fields.associate { it.key to obj.read(it) })
        }
    }

    private fun JsonObject.text(key: String): String = get(key)?.takeIf { it.isJsonPrimitive }?.asString.orEmpty()

    private fun JsonObject.read(field: Field): String =
        when (field.kind) {
            Kind.TEXT, Kind.TEXTAREA -> text(field.key)
            Kind.LINES -> get(field.key)?.takeIf { it.isJsonArray }?.asJsonArray?.joinToString("\n") { it.asString }.orEmpty()
        }

    private fun loadArray(id: String): JsonArray = JsonParser.parseString(content.loadContent().first { it.section.id == id }.json).asJsonArray

    private fun save(
        id: String,
        element: JsonElement,
    ): Result =
        try {
            content.updateContent(id, element.toString())
            Result.Saved
        } catch (error: Exception) {
            Result.Invalid(error.message ?: "Changes were not saved")
        }

    companion object {
        val SCHEMAS =
            listOf(
                Schema(
                    "personal-info",
                    "Personal info",
                    "details",
                    "name",
                    listOf(
                        Field("name", "Name", required = true),
                        Field("title", "Title"),
                        Field("summary", "Summary", Kind.TEXTAREA),
                        Field("location", "Location"),
                        Field("phone", "Phone"),
                        Field("email", "Email"),
                        Field("linkedin", "LinkedIn URL"),
                        Field("github", "GitHub URL"),
                    ),
                    single = true,
                ),
                Schema(
                    "work-experience",
                    "Work experience",
                    "job",
                    "company",
                    listOf(
                        Field("company", "Company", required = true),
                        Field("position", "Position", required = true),
                        Field("location", "Location"),
                        Field("period", "Period"),
                        Field("responsibilities", "Responsibilities (one per line)", Kind.LINES),
                    ),
                ),
                Schema(
                    "skills",
                    "Skills",
                    "skill group",
                    "category",
                    listOf(Field("category", "Category", required = true), Field("items", "Skills (one per line)", Kind.LINES)),
                ),
                Schema(
                    "education",
                    "Education",
                    "degree",
                    "degree",
                    listOf(Field("degree", "Degree", required = true), Field("institution", "Institution"), Field("period", "Period")),
                ),
                Schema(
                    "languages",
                    "Languages",
                    "language",
                    "name",
                    listOf(Field("name", "Language", required = true), Field("level", "Level")),
                ),
            )
    }
}
