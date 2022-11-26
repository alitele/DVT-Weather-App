package com.ciklum.weatherapp.features.home.viewmodel

import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.ciklum.weatherapp.BuildConfig
import com.ciklum.weatherapp.features.main.viewmodel.BaseViewModel
import com.ciklum.weatherapp.extentions.awaitForResult
import com.ciklum.weatherapp.extentions.doNetworkCall
import com.ciklum.weatherapp.extentions.ioJob
import com.ciklum.weatherapp.extentions.onError
import com.ciklum.weatherapp.extentions.onSuccess
import com.ciklum.weatherapp.features.home.model.Forecast
import com.ciklum.weatherapp.features.home.model.Weather
import com.ciklum.weatherapp.utils.SingleLiveEvent
import com.ciklum.weatherapp.network.api.Endpoints
import com.ciklum.weatherapp.utils.preferences.WeatherPrefsImpl
import org.koin.core.component.inject

class HomeViewModel : BaseViewModel() {

    val onFetchWeather = SingleLiveEvent<Weather>()
    val onFetchForecast = SingleLiveEvent<Forecast>()
    val onToggleProgress = SingleLiveEvent<Boolean>()
    val currentLocation = MutableLiveData<Location>()
    private val PERMISSION_ID: Int = 1000

    private val userPrefHelper by inject<WeatherPrefsImpl>()
    private val authApiService by inject<Endpoints>()

    fun fetchCurrentWeather() {
        onToggleProgress.postValue(true)
        val latitude = userPrefHelper.getCurrentLocation().split(",")[0]
        val longitude = userPrefHelper.getCurrentLocation().split(",")[1]

        ioJob {
            doNetworkCall {
                authApiService.fetchCurrentWeather(
                    latitude.toDouble(),
                    longitude.toDouble(),
                    BuildConfig.API_KEY,
                    "metric"
                )
            }.awaitForResult()
                .onSuccess {
                    onFetchWeather.postValue(it)
                    onToggleProgress.postValue(false)
                }
                .onError {
                    onToggleProgress.postValue(false)
                }
        }

    }

    fun fetchForeCastWeather() {
        onToggleProgress.postValue(true)
        val latitude = userPrefHelper.getCurrentLocation().split(",")[0]
        val longitude = userPrefHelper.getCurrentLocation().split(",")[1]

        ioJob {
            doNetworkCall {
                authApiService.fetchForecastWeather(
                    latitude.toDouble(),
                    longitude.toDouble(),
                    BuildConfig.API_KEY,
                    "metric"
                )
            }.awaitForResult()
                .onSuccess {
                    onFetchForecast.postValue(it)
                    onToggleProgress.postValue(false)
                }
                .onError {
                    onToggleProgress.postValue(false)
                }
        }
    }
}