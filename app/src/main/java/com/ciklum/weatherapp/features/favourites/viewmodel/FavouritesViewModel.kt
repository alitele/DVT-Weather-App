package com.ciklum.weatherapp.features.favourites.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.features.favourites.repository.FavouritesRepository
import com.ciklum.weatherapp.features.main.viewmodel.BaseViewModel
import kotlinx.coroutines.launch

class FavouritesViewModel(private val repository: FavouritesRepository) : BaseViewModel() {

    private val locationsList: LiveData<List<LocationEntity>> = repository.getAllLocations().asLiveData()

    fun addNewFavourite(locationEntity: LocationEntity) {
        viewModelScope.launch {
            repository.insertLocation(locationEntity)
        }
    }

    fun removeNewFavourite(locationEntity: LocationEntity) {
        viewModelScope.launch {
            repository.deleteLocation(locationEntity)
        }
    }

    fun getAllFavourites(): LiveData<List<LocationEntity>> {
        return locationsList
    }
}