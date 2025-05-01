package com.example.contactsapp.di

import android.content.ContentResolver
import com.example.data.datasource.ContactDataSourceImlp
import com.example.data.repository.ContactRepositoryImpl
import com.example.domain.datasource.ContactDataSource
import com.example.domain.repository.ContactRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {

    single<ContentResolver> {
        androidContext().contentResolver
    }

    single<ContactDataSource> {
        ContactDataSourceImlp(
            contentResolver = get()
        )
    }

    single<ContactRepository> {
        ContactRepositoryImpl(
            dataSource = get()
        )
    }
}