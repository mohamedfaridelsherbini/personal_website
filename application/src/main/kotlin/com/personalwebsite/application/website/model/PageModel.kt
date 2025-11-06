package com.personalwebsite.application.website.model

import com.personalwebsite.domain.entities.PersonalProject

/**
 * Metadata shared by any rendered HTML page.
 */
data class PageMetadata(
    val title: String,
    val description: String,
    val canonicalUrl: String,
    val socialImageUrl: String,
    val twitterCard: String = "summary_large_image",
    val ogType: String = "website",
    val publishedTime: String? = null,
    val structuredDataJsonLd: String? = null,
)

/**
 * Root sealed model for the different pages the site can render.
 */
sealed class PageModel {
    abstract val metadata: PageMetadata
    abstract val site: WebsiteViewModel

    data class Home(
        override val metadata: PageMetadata,
        override val site: WebsiteViewModel,
    ) : PageModel()

    data class Project(
        val project: PersonalProject,
        override val metadata: PageMetadata,
        override val site: WebsiteViewModel,
    ) : PageModel()
}
