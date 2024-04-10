package com.vaskorr.weatherapp.domain

import androidx.lifecycle.LiveData

interface ForecastsRepository {
    fun getDayForecast(location: String): LiveData<DayForecast>
    fun getWeekForecast(location: String): LiveData<List<DayForecast>>
}