package com.vaskorr.weatherapp.data

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vaskorr.weatherapp.R
import com.vaskorr.weatherapp.data.api.GetForecastAPI
import com.vaskorr.weatherapp.domain.DayForecast
import com.vaskorr.weatherapp.domain.ForecastsRepository
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class ForecastsRepositoryImpl @Inject constructor(
    private val context: Context,
    private val forecastAPI: GetForecastAPI,
    private val dayData: MutableLiveData<DayForecast>,
    private val weekData: MutableLiveData<List<DayForecast>>
) : ForecastsRepository {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDayForecast(location: String): LiveData<DayForecast> {
        thread {
            val query =
                if (location == "") context.resources.getString(R.string.default_city) else location
            dayData.postValue(forecastAPI.getDayForecast(query))
        }
        return dayData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getWeekForecast(location: String): LiveData<List<DayForecast>> {
        thread {
            val query =
                if (location == "") context.resources.getString(R.string.default_city) else location
            weekData.postValue(forecastAPI.getWeekForecast(query))
        }
        return weekData
    }
}