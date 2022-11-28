package com.ciklum.weatherapp.features.main.repository

import android.location.Location

interface MainRepository {
    suspend fun saveCurrentLocation(location: Location)
}