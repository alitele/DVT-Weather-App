package com.ciklum.weatherapp.utils.preferences

interface ReadableTkPrefs {
    fun getCurrentLocation(): String
}

interface WritableTkhPrefs {
    fun setCurrentLocation(location: String)
}

interface WeatherPrefs : ReadableTkPrefs, WritableTkhPrefs
