package com.ciklum.weatherapp.utils.preferences

import com.ciklum.weatherapp.features.favourites.model.Favourite

interface ReadableTkPrefs {
    fun getAllFav(): List<Favourite>
    fun getCurrentLocation(): String
}

interface WritableTkhPrefs {
    fun addFav(favourite: Favourite)
    fun saveAllFav(favourite: List<Favourite>)
    fun removeFav(id: Int)
    fun setCurrentLocation(location: String)
}

interface WeatherPrefs : ReadableTkPrefs, WritableTkhPrefs
