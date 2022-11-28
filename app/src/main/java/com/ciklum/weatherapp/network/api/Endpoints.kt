package com.ciklum.weatherapp.network.api

import com.ciklum.weatherapp.features.home.model.Forecast
import com.ciklum.weatherapp.features.home.model.Weather
import retrofit2.Response
import retrofit2.http.*

interface Endpoints {

    // API to get current weather
    @GET("weather")
    suspend fun fetchCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("q") query:String,
        @Query("appid") api_key: String,
        @Query("units") units:String
    ): Response<Weather>

    // API to get forecast weather
    @GET("forecast")
    suspend fun fetchForecastWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("q") query:String,
        @Query("appid") api_key: String,
        @Query("units") units:String
    ): Response<Forecast>

}