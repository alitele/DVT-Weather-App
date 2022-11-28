package com.ciklum.weatherapp.features.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.features.home.model.Forecast
import com.ciklum.weatherapp.features.home.model.Weather
import com.ciklum.weatherapp.features.home.repository.HomeRepository
import com.ciklum.weatherapp.features.main.viewmodel.BaseViewModel
import com.ciklum.weatherapp.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : BaseViewModel() {

    val onToggleProgress = SingleLiveEvent<Boolean>()

    private val locationsList: LiveData<List<LocationEntity>> =
        repository.getAllLocations().asLiveData()
    val locationWeather = MutableLiveData<LocationEntity>()
    val locationForecast = MutableLiveData<Forecast>()

    fun getWeatherForLocation(id: Int) {
        viewModelScope.launch {
            locationWeather.value=repository.getWeatherForLocation(id)
        }
    }

    fun getForecastForLocation(id: Int) {
        viewModelScope.launch {
            locationForecast.value=repository.getForecastForLocation(id)
        }
    }

    fun getAllFavourites(): LiveData<List<LocationEntity>> {
        return locationsList
    }

}