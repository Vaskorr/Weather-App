package com.vaskorr.weatherapp.presentation

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.vaskorr.weatherapp.R
import com.vaskorr.weatherapp.domain.DayForecast
import com.vaskorr.weatherapp.domain.GetDayForecastUseCase
import com.vaskorr.weatherapp.domain.GetWeekForecastUseCase
import javax.inject.Inject

class WeatherViewModel @Inject constructor(
    val getDayForecastUseCase: GetDayForecastUseCase,
    val getWeekForecastUseCase: GetWeekForecastUseCase,
    val context: Context
) : ViewModel() {
    lateinit var dayData: LiveData<DayForecast>
    lateinit var weekData: LiveData<List<DayForecast>>
    var isWeekForecast = false
    var currentCity = ""

    init {
        currentCity = context.resources.getString(R.string.default_city)
        dayData = getDayForecastUseCase.getDayForecast()
        weekData = getWeekForecastUseCase.getWeekForecast()
    }

    fun onForecastLenghtChange(state: Boolean){
        isWeekForecast = state
        updateData()
    }


    fun updateData(currCity: String = ""){
        if (currCity != ""){
            currentCity = currCity
        }
        if (isWeekForecast){
            getWeekForecastUseCase.getWeekForecast(currentCity)
        }else{
            getDayForecastUseCase.getDayForecast(currentCity)
        }
    }
}

data class WeatherState(
    val city: String,
    val forecast: Any?
)
