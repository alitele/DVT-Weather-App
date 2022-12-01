package com.ciklum.weatherapp.utils.preferences

import android.content.SharedPreferences

class WeatherPrefsImpl(
    sharedPreferences: SharedPreferences,
) : WeatherPrefs {
    var prefStore = SharedPrefStore(sharedPreferences)

    companion object {
        private const val MY_LOCATION = "my_location"
    }

    override fun getCurrentLocation(): String {
        return prefStore.getString(MY_LOCATION,"33.5908823,73.1231582")
    }

    override fun setCurrentLocation(location: String) {
        prefStore.saveString(MY_LOCATION, location)
    }

}