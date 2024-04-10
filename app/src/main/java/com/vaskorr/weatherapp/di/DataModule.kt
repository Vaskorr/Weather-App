package com.vaskorr.weatherapp.di

import androidx.lifecycle.MutableLiveData
import com.vaskorr.weatherapp.domain.DayForecast
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient

@Module
class DataModule {

    @Provides
    fun provideHttpClient(): OkHttpClient{
        return OkHttpClient()
    }

    @Provides
    fun provideDayForecastData(): MutableLiveData<DayForecast> {
        return MutableLiveData<DayForecast>()
    }

    @Provides
    fun provideWeekForecastData(): MutableLiveData<List<DayForecast>> {
        return MutableLiveData<List<DayForecast>>()
    }

}