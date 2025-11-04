package com.personalwebsite.infrastructure.web.view

import com.personalwebsite.application.website.model.PageModel

/**
 * View interface for the website.
 */
interface WebsiteView {
    fun render(page: PageModel): String
    fun renderError(errorMessage: String): String
}
