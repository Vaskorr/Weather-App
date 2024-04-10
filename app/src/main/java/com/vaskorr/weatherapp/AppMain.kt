package com.vaskorr.weatherapp

import android.app.Application
import com.vaskorr.weatherapp.di.ApplicationComponent
import com.vaskorr.weatherapp.di.DaggerApplicationComponent

class AppMain: Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(BuildConfig.weatherApiKey)
    }
}