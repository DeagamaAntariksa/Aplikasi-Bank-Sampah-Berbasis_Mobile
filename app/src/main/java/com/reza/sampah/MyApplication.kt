package com.reza.sampah

import android.app.Application
import com.example.core.di.dataSourceModule
import com.example.core.di.firebaseModule
import com.example.core.di.preferencesModule
import com.example.core.di.repositoryModule
import com.reza.sampah.di.usecaseModule
import com.reza.sampah.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            loadKoinModules(preferencesModule)
            loadKoinModules(firebaseModule)
            loadKoinModules(repositoryModule)
            loadKoinModules(dataSourceModule)
            loadKoinModules(usecaseModule)
            loadKoinModules(viewModelModule)
        }

    }
}