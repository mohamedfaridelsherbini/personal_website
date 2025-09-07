package com.personalwebsite.presentation.views

import com.personalwebsite.presentation.models.WebsiteViewModel

/**
 * View interface for the website
 * Follows Dependency Inversion Principle - depends on abstraction
 * Follows Interface Segregation Principle - only contains necessary methods
 */
interface WebsiteView {
    fun render(viewModel: WebsiteViewModel): String
    fun renderError(errorMessage: String): String
}
