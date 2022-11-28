package com.ciklum.weatherapp.features.main.viewmodel

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewModelScope
import com.ciklum.weatherapp.app.WeatherApp
import com.ciklum.weatherapp.database.db.WeatherDatabase
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.utils.Helper
import com.ciklum.weatherapp.utils.Helper.getCurrentTimestamp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class MainViewModel() : BaseViewModel() {
    val repository by inject<WeatherDatabase>()

    fun saveCurrentLocation(location: Location) {
        viewModelScope.launch(Dispatchers.IO) {
            var currentLocation = repository.weatherDao.getCurrentLocation()

            if (currentLocation == null)
                currentLocation = LocationEntity()

            currentLocation.isMyLocation = true
            currentLocation.lastUpdated=getCurrentTimestamp()
            currentLocation.lat = location.latitude
            currentLocation.lng = location.longitude
            //currentLocation.locationName = Helper.geoCodeLocation(location)
            //val existingLocation=repository.weatherDao.getCurrentLocation()
            repository.weatherDao.saveCurrentLocation(currentLocation)
        }
    }

    fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                WeatherApp.getApp(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                WeatherApp.getApp(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }
}