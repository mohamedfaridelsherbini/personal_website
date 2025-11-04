package com.personalwebsite.presentation.views

import com.personalwebsite.presentation.models.PageModel

/**
 * View interface for the website.
 */
interface WebsiteView {
    fun render(page: PageModel): String
    fun renderError(errorMessage: String): String
}
