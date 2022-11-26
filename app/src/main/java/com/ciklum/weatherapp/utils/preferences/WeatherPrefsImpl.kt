package com.ciklum.weatherapp.utils.preferences

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import android.util.Log
import com.ciklum.weatherapp.features.favourites.model.Favourite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WeatherPrefsImpl(
    sharedPreferences: SharedPreferences,
) : WeatherPrefs {
    var prefStore = SharedPrefStore(sharedPreferences)

    companion object {
        private const val FAVOURITES = "favourite"
        private const val MY_LOCATION = "my_location"
    }

    override fun getAllFav(): List<Favourite> {
        return if(prefStore.getString(FAVOURITES).isEmpty())
            mutableListOf()
        else
        {
            Log.d(TAG, "getAllFav: ${prefStore.getString(FAVOURITES)}")
            Gson().fromJson<ArrayList<Favourite>>(
                prefStore.getString(FAVOURITES), TypeToken.getParameterized(
                    ArrayList::class.java,
                    Favourite::class.java
                ).type
            )
        }
    }

    override fun getCurrentLocation(): String {
        return prefStore.getString(MY_LOCATION,"33.5908823,73.1231582")
    }

    override fun setCurrentLocation(location: String) {
        prefStore.saveString(MY_LOCATION, location)
    }

    override fun addFav(favourite: Favourite) {
        val existingFav = getAllFav() as MutableList
        existingFav.add(favourite)
        saveAllFav(existingFav)
    }

    override fun saveAllFav(favourite: List<Favourite>) {
        prefStore.saveString(FAVOURITES, Gson().toJson(favourite))
    }

    override fun removeFav(id: Int) {
        val existingFav = getAllFav() as MutableList
        existingFav.remove(existingFav.find { f->f.id == id})
        saveAllFav(existingFav)
    }

}