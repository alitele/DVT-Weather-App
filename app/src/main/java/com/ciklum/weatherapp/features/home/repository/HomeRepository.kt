package com.ciklum.weatherapp.features.home.repository

import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.features.home.model.Forecast
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAllLocations(): Flow<List<LocationEntity>>
    suspend fun getWeatherForLocation(id: Int): LocationEntity
    suspend fun getForecastForLocation(id: Int): Forecast
}