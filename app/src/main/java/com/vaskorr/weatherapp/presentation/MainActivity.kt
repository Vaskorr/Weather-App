package com.vaskorr.weatherapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import com.example.compose.WeatherAppTheme
import com.vaskorr.weatherapp.AppMain
import com.vaskorr.weatherapp.domain.GetDayForecastUseCase
import com.vaskorr.weatherapp.domain.GetWeekForecastUseCase
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    private val component by lazy {
        (application as AppMain).component
    }

    @Inject
    lateinit var dayForecast: GetDayForecastUseCase

    @Inject
    lateinit var weekForecast: GetWeekForecastUseCase

    @Inject
    lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                Box(
                    modifier = Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    WeatherScreen(weatherViewModel)
                }
            }
        }
    }
}