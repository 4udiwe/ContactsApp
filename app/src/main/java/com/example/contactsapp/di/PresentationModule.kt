package com.example.contactsapp.di

import com.example.presentation.viewmodel.ContactsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<ContactsViewModel> {
        ContactsViewModel(useCase = get())
    }
}