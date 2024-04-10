package com.vaskorr.weatherapp.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.vaskorr.weatherapp.domain.DayForecast
import com.vaskorr.weatherapp.domain.HourForecast
import java.time.format.TextStyle
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val dayForecast = viewModel.dayData.observeAsState().value
    val weekForecast = viewModel.weekData.observeAsState().value
    var currCity by remember {
        mutableStateOf("")
    }
    var isWeekForecast by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = currCity,
            onValueChange = {v -> currCity = v},
            label = { Text("Поиск по городам...") },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {viewModel.updateData(currCity)}),
            modifier = Modifier.fillMaxWidth()
        )
        Row(horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()){
            Text(text = "Прогноз на неделю", color = MaterialTheme.colorScheme.onBackground)
            Switch(checked = isWeekForecast, onCheckedChange = {isWeekForecast = it; viewModel.onForecastLenghtChange(it)})
        }
        Spacer(modifier = Modifier.height(16.dp))

        if (!viewModel.isWeekForecast && dayForecast != null){
            DayForecastItem(dayForecast)
        }else if (viewModel.isWeekForecast && weekForecast != null){
            LazyColumn {
                items(weekForecast) {
                    DayForecastItem(it)
                }
            }
        }
        else{
            Text(text = "Не удается найти город", color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayForecastItem(dayForecast: DayForecast) {
    Card (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(220.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(MaterialTheme.colorScheme.secondaryContainer)
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = dayForecast.location, style = MaterialTheme.typography.headlineLarge, color = MaterialTheme.colorScheme.onSecondaryContainer)
            AsyncImage(model = "https:"+dayForecast.icon, contentDescription = "icon")
            Text(text = "${dayForecast.datetime.dayOfMonth} ${dayForecast.datetime.month.getDisplayName(TextStyle.FULL, Locale("ru"))}", style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
            Text(text = dayForecast.condition, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.onSecondaryContainer)
            HourForecastCarousel(hourForecasts = dayForecast.hours)
        }
    }
}

@Composable
fun HourForecastCarousel(hourForecasts: List<HourForecast>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ){
        items(hourForecasts) { hourForecast ->
            HourForecastItem(hourForecast)
        }
    }
}

@Composable
fun HourForecastItem(hourForecast: HourForecast) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .width(80.dp)
            .height(80.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(model = "https:"+hourForecast.icon, contentDescription = "icon")
        Text(text = hourForecast.datetime.toString().split("T")[1], style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondaryContainer)
        Text(text = "${hourForecast.temp}°", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondaryContainer)
        Row {
            AsyncImage(model = "https://cdn.weatherapi.com/weather/64x64/day/308.png", contentDescription = "icon")
            Text(text = "${hourForecast.chanceOfRain}%", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSecondaryContainer)
        }
    }
}
