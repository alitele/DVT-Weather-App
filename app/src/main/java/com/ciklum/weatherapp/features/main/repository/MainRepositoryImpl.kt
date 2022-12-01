package com.ciklum.weatherapp.features.main.repository

import com.ciklum.weatherapp.database.daos.LocationDao
import com.ciklum.weatherapp.database.entities.LocationEntity

class MainRepositoryImpl(
    private val dao: LocationDao
) :
    MainRepository {

    override suspend fun deleteCurrLocations() {
        dao.deleteCurrLocations()
    }

    override suspend fun getCurrLocation(): LocationEntity {
        return if (dao.getCurrentLocation() == null)
            LocationEntity()
        else
            dao.getCurrentLocation()
    }

    override suspend fun saveCurrentLocation(locationEntity: LocationEntity) {
        dao.saveCurrentLocation(locationEntity)
    }


}