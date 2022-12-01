package com.ciklum.weatherapp.features.main.viewmodel

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import com.ciklum.weatherapp.app.WeatherApp
import com.ciklum.weatherapp.commons.Constants.MIN_LOCATION_UPDATE_INTERVAL
import com.ciklum.weatherapp.extentions.ioJob
import com.ciklum.weatherapp.features.main.repository.MainRepository
import com.ciklum.weatherapp.utils.Helper
import com.ciklum.weatherapp.utils.Helper.getCurrentTimestamp
import java.util.*

class MainViewModel(private val repository: MainRepository) : BaseViewModel() {

    fun saveCurrentLocation(location: Location) {
        ioJob {
            val currentLocation = repository.getCurrLocation()
            currentLocation.isMyLocation = true
            currentLocation.lastUpdated = getCurrentTimestamp()
            currentLocation.lat = location.latitude
            currentLocation.lng = location.longitude
            currentLocation.locationName = Helper.geoCodeLocation(location)
            repository.saveCurrentLocation(currentLocation)
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