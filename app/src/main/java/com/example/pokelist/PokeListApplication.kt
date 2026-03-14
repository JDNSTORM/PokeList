package com.example.pokelist

import android.app.Application
import com.example.pokelist.viewmodels.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class PokeListApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(applicationContext)
            modules(
                viewModelModule
            )
        }
    }
}