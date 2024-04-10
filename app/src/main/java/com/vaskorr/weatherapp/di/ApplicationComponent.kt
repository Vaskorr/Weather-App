package com.vaskorr.weatherapp.di

import android.app.Activity
import android.content.Context
import com.vaskorr.weatherapp.presentation.MainActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, DomainModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)

    @Component.Factory
    interface ApplicationComponentFactory{
        fun create(
            @BindsInstance
            apiKey: String
        ): ApplicationComponent
    }
}