package com.ciklum.weatherapp.features.favourites.repository

import com.ciklum.weatherapp.database.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

interface FavouritesRepository {
    fun getAllLocations(): Flow<List<LocationEntity>>
    suspend fun deleteLocation(locationEntity: LocationEntity)
    suspend fun insertLocation(locationEntity: LocationEntity)
}