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
 * Much cleaner than manual DI - learned this from the Koin docs
 * TODO: Maybe split this into smaller modules if it gets too big
 */
val appModule = module {
    
    // Repository implementations - keeping it simple with hardcoded data for now
    single<PersonalInfoRepository> { PersonalInfoRepositoryImpl() }
    single<SkillRepository> { SkillRepositoryImpl() }
    single<WorkExperienceRepository> { WorkExperienceRepositoryImpl() }
    single<PersonalProjectRepository> { PersonalProjectRepositoryImpl() }
    single<LanguageRepository> { LanguageRepositoryImpl() }
    
    // Use cases - these are pretty straightforward
    single { GetPersonalInfoUseCase(get()) }
    single { GetSkillsUseCase(get()) }
    single { GetWorkExperienceUseCase(get()) }
    single { GetPersonalProjectsUseCase(get()) }
    single { GetLanguagesUseCase(get()) }
    
    // View - only have HTML view for now, might add others later
    single<WebsiteView> { HtmlWebsiteView() }
    
    // Controller - wires everything together
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
