package com.personalwebsite.di

import com.personalwebsite.application.website.WebsiteController
import com.personalwebsite.infrastructure.cache.ContentCache
import com.personalwebsite.infrastructure.content.ContentLoader
import com.personalwebsite.infrastructure.content.LanguageRepositoryImpl
import com.personalwebsite.infrastructure.content.PersonalInfoRepositoryImpl
import com.personalwebsite.infrastructure.content.PersonalProjectRepositoryImpl
import com.personalwebsite.infrastructure.content.SkillRepositoryImpl
import com.personalwebsite.infrastructure.content.WorkExperienceRepositoryImpl
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
import com.personalwebsite.infrastructure.web.view.HtmlWebsiteView
import com.personalwebsite.infrastructure.web.view.WebsiteView
import com.google.gson.Gson
import org.koin.dsl.module

/**
 * Koin module for dependency injection
 * Much cleaner than manual DI - learned this from the Koin docs
 */
val appModule = module {
    
    // Content loader stack
    single { Gson() }
    single { ContentLoader(gson = get()) }
    single { ContentCache() }

    // Repository implementations backed by structured content files
    single<PersonalInfoRepository> { PersonalInfoRepositoryImpl(contentLoader = get()) }
    single<SkillRepository> { SkillRepositoryImpl(contentLoader = get()) }
    single<WorkExperienceRepository> { WorkExperienceRepositoryImpl(contentLoader = get()) }
    single<PersonalProjectRepository> { PersonalProjectRepositoryImpl(contentLoader = get()) }
    single<LanguageRepository> { LanguageRepositoryImpl(contentLoader = get()) }
    
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
            websiteView = get(),
            contentCache = get()
        )
    }
}
