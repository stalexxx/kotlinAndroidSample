package com.aostrovskiy.hotels.di.component

import com.aostrovskiy.hotels.di.module.ApplicationModule
import com.aostrovskiy.hotels.di.module.HttpModule
import com.aostrovskiy.hotels.view.activity.MainActivity
import com.aostrovskiy.hotels.view.fragment.DetailsFragment
import com.aostrovskiy.hotels.view.fragment.ListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(HttpModule::class, ApplicationModule::class))
interface AppComponent {
    fun inject(fragment: MainActivity)

    fun inject(fragment: DetailsFragment)

    fun inject(fragment: ListFragment)
}
