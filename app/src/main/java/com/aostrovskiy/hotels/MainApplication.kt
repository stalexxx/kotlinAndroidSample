package com.aostrovskiy.hotels

import android.app.Application

import com.aostrovskiy.hotels.di.component.AppComponent
import com.aostrovskiy.hotels.di.component.DaggerAppComponent
import com.aostrovskiy.hotels.di.module.ApplicationModule
import com.aostrovskiy.hotels.di.module.HttpModule

class MainApplication : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()

        component = DaggerAppComponent.builder()
                .applicationModule(ApplicationModule(this))
                .httpModule(HttpModule())
                .build()

        instance = this
    }

    companion object {
        lateinit var instance: MainApplication
    }
}

