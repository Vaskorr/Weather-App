package com.vaskorr.weatherapp.di

import com.vaskorr.weatherapp.data.ForecastsRepositoryImpl
import com.vaskorr.weatherapp.domain.ForecastsRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindForecastsRepository(impl: ForecastsRepositoryImpl): ForecastsRepository
}