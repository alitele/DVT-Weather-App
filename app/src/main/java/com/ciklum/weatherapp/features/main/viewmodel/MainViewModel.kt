package com.ciklum.weatherapp.features.main.viewmodel

import android.location.Location
import com.ciklum.weatherapp.utils.preferences.WeatherPrefsImpl
import org.koin.core.component.inject

class MainViewModel : BaseViewModel() {
    private val userPrefHelper by inject<WeatherPrefsImpl>()

    fun saveCurrentLocation(location: Location) {
        userPrefHelper.setCurrentLocation("${location.latitude},${location.longitude}")
    }
}