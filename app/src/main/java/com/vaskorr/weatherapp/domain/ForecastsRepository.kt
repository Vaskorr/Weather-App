package com.vaskorr.weatherapp.domain

import androidx.lifecycle.LiveData
import javax.security.auth.callback.Callback

interface ForecastsRepository {
    fun getDayForecast(location: String): LiveData<DayForecast>
    fun getWeekForecast(location: String): LiveData<List<DayForecast>>
}