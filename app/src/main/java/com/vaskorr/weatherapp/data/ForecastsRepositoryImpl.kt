package com.vaskorr.weatherapp.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vaskorr.weatherapp.BuildConfig
import com.vaskorr.weatherapp.data.json.Day
import com.vaskorr.weatherapp.data.json.JsonAdapter
import com.vaskorr.weatherapp.di.ApplicationComponent
import com.vaskorr.weatherapp.domain.DayForecast
import com.vaskorr.weatherapp.domain.ForecastsRepository
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

@Singleton
class ForecastsRepositoryImpl @Inject constructor(
    private val client: OkHttpClient,
    private val dayData: MutableLiveData<DayForecast>,
    private val weekData: MutableLiveData<List<DayForecast>>,
    private val jsonAdapter: JsonAdapter,
    private val apiKey: String
): ForecastsRepository{

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDayForecast(q: String): LiveData<DayForecast>{
        thread {
            val request = Request.Builder()
                .url("https://api.weatherapi.com/v1/forecast.json?q=$q&days=1&lang=ru&alerts=no&aqi=no&key=$apiKey")
                .addHeader("accept", "application/json")
                .build()

            var body: String = ""

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException("Запрос к серверу не был успешен:" +
                                " ${response.code} ${response.message}")
                    }
                    body = response.body!!.string()
                    dayData.postValue(jsonAdapter.getDayForecast(body))
                }
            } catch (e: IOException) {
                println("Ошибка подключения: $e")
            }
        }
        return dayData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getWeekForecast(q: String): LiveData<List<DayForecast>>{
        thread {
            val request = Request.Builder()
                .url("https://api.weatherapi.com/v1/forecast.json?q=$q&days=7&lang=ru&alerts=no&aqi=no&key=$apiKey")
                .addHeader("accept", "application/json")
                .build()

            var body: String = ""

            try {
                client.newCall(request).execute().use { response ->
                    if (!response.isSuccessful) {
                        throw IOException("Запрос к серверу не был успешен:" +
                                " ${response.code} ${response.message}")
                    }
                    body = response.body!!.string()
                    weekData.postValue(jsonAdapter.getWeekForecast(body))
                }
            } catch (e: IOException) {
                println("Ошибка подключения: $e")
            }
        }
        return weekData
    }
}