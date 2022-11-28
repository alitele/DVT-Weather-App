package com.ciklum.weatherapp.features.home.repository

import android.location.Location
import androidx.lifecycle.LiveData
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.features.home.model.Forecast
import com.ciklum.weatherapp.utils.preferences.AppResult
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    fun getAllLocations(): Flow<List<LocationEntity>>
    suspend fun getWeatherForLocation(id: Int): LocationEntity
    suspend fun getForecastForLocation(id: Int): Forecast
}