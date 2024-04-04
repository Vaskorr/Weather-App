package com.vaskorr.weatherapp.domain

import javax.inject.Inject

class GetDayForecastUseCase @Inject constructor(){

    @Inject
    lateinit var forecastsRepository: ForecastsRepository

    fun getDayForecast(location: String = ""): DayForecast{
        return forecastsRepository.getDayForecast(location)
    }
}