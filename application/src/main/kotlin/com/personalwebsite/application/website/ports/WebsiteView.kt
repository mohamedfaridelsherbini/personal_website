package com.personalwebsite.application.website.ports

import com.personalwebsite.application.website.model.PageModel

/**
 * Outbound port for rendering HTML.
 */
interface WebsiteView {
    fun render(page: PageModel): String
    fun renderError(errorMessage: String): String
}
