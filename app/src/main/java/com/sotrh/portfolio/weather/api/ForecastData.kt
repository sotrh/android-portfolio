package com.sotrh.portfolio.weather.api

data class ForecastData(
    val type: String,
    val properties: ForecastProperties
)

data class ForecastProperties(
    val units: String,
    val periods: List<ForecastPeriod>
)

data class ForecastPeriod(
    val number: Int,
    val name: String,
    // TODO: figure our startTime and endTime as dates
    val startTime: String,
    val endTime: String,
    val isDaytime: Boolean,
    val temperature: Int,
    val temperatureUnit: String,
    val icon: String,
    val shortForecast: String,
    val detailedForecast: String
)
