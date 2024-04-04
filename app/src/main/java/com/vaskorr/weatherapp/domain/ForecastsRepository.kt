package com.vaskorr.weatherapp.domain

interface ForecastsRepository {
    fun getDayForecast(location: String): DayForecast
    fun getWeekForecast(location: String): List<DayForecast>
}