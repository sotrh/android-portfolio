package com.sotrh.portfolio.weather.model

data class ForecastModel(
    val gridX: Int,
    val gridY: Int,
    val city: String,
    val state: String,
    val cwa: String,
    val units: String,
    val name: String,
    val daytime: Boolean,
    val temperature: Int,
    val temperatureUnit: String,
    val icon: String,
    val shortForecast: String,
    val detailedForecast: String
)