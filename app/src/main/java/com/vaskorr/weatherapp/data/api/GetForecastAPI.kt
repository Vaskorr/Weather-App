package com.vaskorr.weatherapp.data.api

import com.vaskorr.weatherapp.domain.DayForecast

interface GetForecastAPI {
    fun getDayForecast(location: String): DayForecast?

    fun getWeekForecast(location: String): List<DayForecast>?
}