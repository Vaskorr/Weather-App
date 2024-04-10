package com.vaskorr.weatherapp.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetDayForecastUseCase @Inject constructor(
    val forecastsRepository: ForecastsRepository
) {

    fun getDayForecast(location: String = ""): LiveData<DayForecast> {
        return forecastsRepository.getDayForecast(location)
    }
}