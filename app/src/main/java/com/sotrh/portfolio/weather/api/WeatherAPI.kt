package com.sotrh.portfolio.weather.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred

interface WeatherAPI {
    @GET("points/{lat},{lon}")
    fun getOfficeByLocationAsync(
        @Path("lat") lat: Double,
        @Path("lon") lon: Double
    ): Deferred<OfficeByLocationData>

    @GET("gridpoints/{office}/{gridX},{gridY}/forecast")
    fun getForecastAsync(
        @Path("office") office: String,
        @Path("gridX") gridX: Int,
        @Path("gridY") gridY: Int
    ): Deferred<ForecastData>

    companion object {
        private val httpClient = OkHttpClient.Builder()
        private val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl("https://api.weather.gov/")
            .build()
        val service: WeatherAPI = retrofit.create(WeatherAPI::class.java)
    }
}