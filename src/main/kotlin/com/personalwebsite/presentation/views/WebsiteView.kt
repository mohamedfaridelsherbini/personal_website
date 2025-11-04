package com.personalwebsite.presentation.views

import com.personalwebsite.domain.entities.PersonalProject
import com.personalwebsite.presentation.models.WebsiteViewModel

/**
 * View interface for the website
 */
interface WebsiteView {
    fun render(viewModel: WebsiteViewModel): String
    fun renderProject(project: PersonalProject, viewModel: WebsiteViewModel): String
    fun renderError(errorMessage: String): String
}
