package com.example.contactsapp.app

import android.app.Application
import com.example.contactsapp.di.dataModule
import com.example.contactsapp.di.domainModule
import com.example.contactsapp.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.INFO)
            androidContext(this@App)
            modules(
                listOf(dataModule, domainModule, presentationModule)
            )
        }
    }
}