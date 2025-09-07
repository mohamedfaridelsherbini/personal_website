package com.personalwebsite.di

import com.personalwebsite.data.repositories.*
import com.personalwebsite.domain.repositories.*
import com.personalwebsite.domain.usecases.*
import com.personalwebsite.presentation.controllers.WebsiteController
import com.personalwebsite.presentation.views.HtmlWebsiteView
import com.personalwebsite.presentation.views.WebsiteView
import org.koin.dsl.module

/**
 * Koin module for dependency injection
 * Significantly reduces boilerplate code compared to manual DI
 * Follows Single Responsibility Principle - only handles dependency configuration
 */
val appModule = module {
    
    // Repository implementations
    single<PersonalInfoRepository> { PersonalInfoRepositoryImpl() }
    single<SkillRepository> { SkillRepositoryImpl() }
    single<WorkExperienceRepository> { WorkExperienceRepositoryImpl() }
    single<PersonalProjectRepository> { PersonalProjectRepositoryImpl() }
    single<LanguageRepository> { LanguageRepositoryImpl() }
    
    // Use cases
    single { GetPersonalInfoUseCase(get()) }
    single { GetSkillsUseCase(get()) }
    single { GetWorkExperienceUseCase(get()) }
    single { GetPersonalProjectsUseCase(get()) }
    single { GetLanguagesUseCase(get()) }
    
    // View
    single<WebsiteView> { HtmlWebsiteView() }
    
    // Controller
    single { 
        WebsiteController(
            getPersonalInfoUseCase = get(),
            getSkillsUseCase = get(),
            getWorkExperienceUseCase = get(),
            getPersonalProjectsUseCase = get(),
            getLanguagesUseCase = get(),
            websiteView = get()
        )
    }
}
