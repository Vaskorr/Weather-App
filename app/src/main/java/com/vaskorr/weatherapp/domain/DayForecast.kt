package com.vaskorr.weatherapp.domain

import java.time.LocalDate

data class DayForecast (
    val location: String,
    val datetime: LocalDate,
    val condition: String,
    val icon: String,
    val hours: List<HourForecast>
)