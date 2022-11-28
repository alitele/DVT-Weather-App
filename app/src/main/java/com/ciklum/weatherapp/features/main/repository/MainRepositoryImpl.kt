package com.ciklum.weatherapp.features.main.repository

import android.content.Context
import android.location.Location
import com.ciklum.weatherapp.database.daos.LocationDao
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.utils.Helper
import kotlinx.coroutines.flow.Flow

class MainRepositoryImpl(
    private val context: Context,
    private val dao: LocationDao
) :
    MainRepository {

    override suspend fun saveCurrentLocation(location: Location) {
        var currentLocation = dao.getCurrentLocation()

        if (currentLocation == null)
            currentLocation = LocationEntity()

        currentLocation.isMyLocation = true
        currentLocation.lat = location.latitude
        currentLocation.lng = location.longitude
        currentLocation.locationName = Helper.geoCodeLocation(location)

        dao.saveCurrentLocation(currentLocation)
    }

}