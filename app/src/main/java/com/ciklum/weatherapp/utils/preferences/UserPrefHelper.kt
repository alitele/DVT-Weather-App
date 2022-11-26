package com.ciklum.weatherapp.utils.preferences

import android.content.SharedPreferences
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.ciklum.weatherapp.features.favourites.model.Favourite
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UserPrefHelper(
    sharedPreferences: SharedPreferences,
) : WeatherPrefs {
    var prefStore = SharedPrefStore(sharedPreferences)

    companion object {
        private const val FAVOURITES = "favourite"
        private const val MY_LOCATION = "my_location"
    }

    override fun getAllFav(): List<Favourite> {
        return if (prefStore.getString(FAVOURITES).isNullOrEmpty())
            mutableListOf()
        else {
            Log.d("prefs",prefStore.getString(FAVOURITES).toString())
            //return emptyList()
            Gson().fromJson<ArrayList<Favourite>>(
                prefStore.getString(FAVOURITES), TypeToken.getParameterized(
                    ArrayList::class.java,
                    Favourite::class.java
                ).type
            )
        }
    }

    override fun getCurrentLocation(): String {
        return prefStore.getString(MY_LOCATION)
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
        prefStore.saveString(FAVOURITES, Gson().toJson(favourite.toString()))
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun removeFav(id: Int) {
        val existingFav = getAllFav() as MutableList
        existingFav.removeIf { f -> f.id == id }
        saveAllFav(existingFav)
    }

}