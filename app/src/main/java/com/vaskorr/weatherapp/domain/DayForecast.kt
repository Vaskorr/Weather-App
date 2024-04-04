package com.vaskorr.weatherapp.domain

import java.util.Date

data class DayForecast (
    val location: String,
    val datetime: Date,
    val condition: String,
    val icon: String,
    val hours: List<HourForecast>
)