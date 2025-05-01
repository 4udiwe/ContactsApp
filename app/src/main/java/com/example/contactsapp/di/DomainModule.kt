package com.example.contactsapp.di

import com.example.domain.usecase.GetContactsUseCase
import org.koin.dsl.module

val domainModule = module {

    single<GetContactsUseCase> {
        GetContactsUseCase(
            repository = get()
        )
    }
}