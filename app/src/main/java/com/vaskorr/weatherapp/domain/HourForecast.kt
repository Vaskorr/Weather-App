package com.vaskorr.weatherapp.domain

import java.time.LocalDateTime

data class HourForecast(
    val datetime: LocalDateTime,
    val temp: Double,
    val icon: String,
    val chanceOfRain: Double
)
