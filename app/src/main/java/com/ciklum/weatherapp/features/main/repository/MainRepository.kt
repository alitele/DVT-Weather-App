package com.ciklum.weatherapp.features.main.repository

import com.ciklum.weatherapp.database.entities.LocationEntity

interface MainRepository {
    suspend fun deleteCurrLocations()
    suspend fun getCurrLocation(): LocationEntity
    suspend fun saveCurrentLocation(locationEntity: LocationEntity)
}