package com.ciklum.weatherapp.features.favourites.viewmodel

import com.ciklum.weatherapp.features.main.viewmodel.BaseViewModel
import com.ciklum.weatherapp.features.favourites.model.Favourite
import com.ciklum.weatherapp.utils.SingleLiveEvent
import com.ciklum.weatherapp.utils.preferences.WeatherPrefsImpl
import org.koin.core.component.inject


class FavouritesViewModel : BaseViewModel() {

    val onFetchFavourites = SingleLiveEvent<List<Favourite>>()
    private val userPrefHelper by inject<WeatherPrefsImpl>()

    fun fetchFavourites() {
        onFetchFavourites.postValue(userPrefHelper.getAllFav())
    }

    fun addNewFavourite(favourite: Favourite) {
        if (!userPrefHelper.getAllFav().contains(favourite)) {
            userPrefHelper.addFav(favourite)
            fetchFavourites()
        }
    }

    fun removeNewFavourite(favourite: Favourite) {
        userPrefHelper.removeFav(favourite.id)
        fetchFavourites()
    }
}