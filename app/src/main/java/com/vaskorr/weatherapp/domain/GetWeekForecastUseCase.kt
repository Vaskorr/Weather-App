package com.vaskorr.weatherapp.domain

import javax.inject.Inject

class GetWeekForecastUseCase @Inject constructor() {

    @Inject
    lateinit var forecastsRepository: ForecastsRepository

    fun getWeekForecast(location: String = ""): List<DayForecast>{
        return forecastsRepository.getWeekForecast(location)
    }
}