package com.ciklum.weatherapp.features.favourites.repository

import com.ciklum.weatherapp.database.daos.LocationDao
import com.ciklum.weatherapp.database.entities.LocationEntity
import kotlinx.coroutines.flow.Flow

class FavouritesRepositoryImpl(
    private val dao: LocationDao
) :
    FavouritesRepository {
    override fun getAllLocations(): Flow<List<LocationEntity>> {
        return dao.getAllLocations()
    }

    override suspend fun deleteLocation(locationEntity: LocationEntity) {
        dao.deleteLocation(locationEntity)
    }

    override suspend fun insertLocation(locationEntity: LocationEntity) {
        dao.insertLocation(locationEntity)
    }

}