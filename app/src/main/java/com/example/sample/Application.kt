package com.example.sample

import com.example.sample.di.appModule
import org.koin.core.context.startKoin

class Application : android.app.Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                appModule
            )
        }
    }
}