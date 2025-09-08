package com.personalwebsite.di

import com.personalwebsite.data.repositories.PersonalInfoRepositoryImpl
import com.personalwebsite.data.repositories.SkillRepositoryImpl
import com.personalwebsite.data.repositories.WorkExperienceRepositoryImpl
import com.personalwebsite.data.repositories.PersonalProjectRepositoryImpl
import com.personalwebsite.data.repositories.LanguageRepositoryImpl
import com.personalwebsite.domain.repositories.PersonalInfoRepository
import com.personalwebsite.domain.repositories.SkillRepository
import com.personalwebsite.domain.repositories.WorkExperienceRepository
import com.personalwebsite.domain.repositories.PersonalProjectRepository
import com.personalwebsite.domain.repositories.LanguageRepository
import com.personalwebsite.domain.usecases.GetPersonalInfoUseCase
import com.personalwebsite.domain.usecases.GetSkillsUseCase
import com.personalwebsite.domain.usecases.GetWorkExperienceUseCase
import com.personalwebsite.domain.usecases.GetPersonalProjectsUseCase
import com.personalwebsite.domain.usecases.GetLanguagesUseCase
import com.personalwebsite.presentation.controllers.WebsiteController
import com.personalwebsite.presentation.views.HtmlWebsiteView
import com.personalwebsite.presentation.views.WebsiteView
import org.koin.dsl.module

/**
 * Koin module for dependency injection
 * Much cleaner than manual DI - learned this from the Koin docs
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
