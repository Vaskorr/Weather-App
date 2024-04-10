package com.vaskorr.weatherapp.domain

import androidx.lifecycle.LiveData
import javax.inject.Inject

class GetWeekForecastUseCase @Inject constructor(
    val forecastsRepository: ForecastsRepository
) {

    fun getWeekForecast(location: String = ""): LiveData<List<DayForecast>> {
        return forecastsRepository.getWeekForecast(location)
    }
}