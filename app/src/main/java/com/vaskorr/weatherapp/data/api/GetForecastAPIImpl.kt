package com.vaskorr.weatherapp.data.api

import android.os.Build
import androidx.annotation.RequiresApi
import com.vaskorr.weatherapp.data.json.JsonAdapter
import com.vaskorr.weatherapp.domain.DayForecast
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import javax.inject.Inject

class GetForecastAPIImpl @Inject constructor(
    private val client: OkHttpClient,
    private val jsonAdapter: JsonAdapter,
    private val apiKey: String
): GetForecastAPI {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun getDayForecast(location: String): DayForecast? {
        val request = Request.Builder()
            .url("https://api.weatherapi.com/v1/forecast.json?q=$location&days=1&lang=ru&alerts=no&aqi=no&key=$apiKey")
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
                return jsonAdapter.getDayForecast(body)
            }
        } catch (e: IOException) {
            println("Ошибка подключения: $e")
        }
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getWeekForecast(location: String): List<DayForecast>? {
        val request = Request.Builder()
            .url("https://api.weatherapi.com/v1/forecast.json?q=$location&days=7&lang=ru&alerts=no&aqi=no&key=$apiKey")
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
                return jsonAdapter.getWeekForecast(body)
            }
        } catch (e: IOException) {
            println("Ошибка подключения: $e")
        }
        return null
    }

}