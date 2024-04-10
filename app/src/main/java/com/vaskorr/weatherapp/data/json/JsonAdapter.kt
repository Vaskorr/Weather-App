package com.vaskorr.weatherapp.data.json

import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.vaskorr.weatherapp.domain.DayForecast
import com.vaskorr.weatherapp.domain.HourForecast
import dagger.internal.InjectedFieldSignature
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class JsonAdapter @Inject constructor(){
    private fun getForecastData(jsonString: String): ForecastData {
        return Gson().fromJson(jsonString, ForecastData::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayForecast(jsonString: String, dayNumber: Int = 0): DayForecast {
        val forecastData = getForecastData(jsonString)
        val hours = mutableListOf<HourForecast>()

        forecastData.forecast?.forecastday?.get(dayNumber)?.hour?.forEach {
            val hourForecast = HourForecast(
                LocalDateTime.parse(
                    it.time ?: "",
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
                ),
                it.tempC ?: 0.0,
                it.condition?.icon ?: "",
                it.chanceOfRain ?: 0.0
            )
            hours.add(hourForecast)
        }

        return DayForecast(
            forecastData.location?.name ?: "",
            LocalDate.parse(
                forecastData.forecast?.forecastday?.get(dayNumber)?.date ?: "",
                DateTimeFormatter.ofPattern("yyyy-MM-dd")
            ),
            forecastData.forecast?.forecastday?.get(dayNumber)?.day?.condition?.text ?: "",
            forecastData.forecast?.forecastday?.get(dayNumber)?.day?.condition?.icon ?: "",
            hours
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeekForecast(jsonString: String): List<DayForecast>{
        val weekForecast = mutableListOf<DayForecast>()
        for(i in 0 until 7){
            val dayForecast = getDayForecast(jsonString, i)
            weekForecast.add(dayForecast)
        }
        return weekForecast
    }
}