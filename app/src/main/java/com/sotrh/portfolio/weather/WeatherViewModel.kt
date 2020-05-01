package com.sotrh.portfolio.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sotrh.portfolio.weather.api.WeatherAPI
import com.sotrh.portfolio.weather.model.ForecastModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherViewModel : ViewModel() {
    fun getForecastPeriods(lat: Double, lon: Double): MutableLiveData<ForecastModel> {
        val results = MutableLiveData<ForecastModel>()

        viewModelScope.launch {
            val forecast = withContext(Dispatchers.IO) {
                val officeData = WeatherAPI.service.getOfficeByLocationAsync(lat, lon).await()
                val officeProperties = officeData.properties
                val cwa = officeProperties.cwa
                val gridX = officeProperties.gridX
                val gridY = officeProperties.gridY
                val location = officeProperties.relativeLocation
                val city = location.properties.city
                val state = location.properties.state

                val forecastData = WeatherAPI.service.getForecastAsync(cwa, gridX, gridY).await()
                val units = forecastData.properties.units
                // TODO: handle there not being a forecast
                val forecast = forecastData.properties.periods.first()

                ForecastModel(
                    gridX,
                    gridY,
                    city,
                    state,
                    cwa,
                    units,
                    forecast.name,
                    forecast.isDaytime,
                    forecast.temperature,
                    forecast.temperatureUnit,
                    forecast.icon,
                    forecast.shortForecast,
                    forecast.detailedForecast
                )
            }

            results.value = forecast
        }

        return results
    }
}