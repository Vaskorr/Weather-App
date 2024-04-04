package com.vaskorr.weatherapp.domain

import java.util.Date

data class HourForecast(
    val datetime: Date,
    val temp: Int,
    val icon: String,
    val chanceOfRain: String
)
