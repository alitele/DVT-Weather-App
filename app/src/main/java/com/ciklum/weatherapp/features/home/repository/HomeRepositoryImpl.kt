package com.ciklum.weatherapp.features.home.repository

import android.util.Log
import com.ciklum.weatherapp.BuildConfig
import com.ciklum.weatherapp.app.WeatherApp
import com.ciklum.weatherapp.commons.Constants.UNIT_TYPE
import com.ciklum.weatherapp.database.daos.LocationDao
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.extentions.notNull
import com.ciklum.weatherapp.features.home.model.Forecast
import com.ciklum.weatherapp.network.api.Endpoints
import com.ciklum.weatherapp.utils.Helper.getCurrentTimestamp
import com.ciklum.weatherapp.utils.NetworkManager.isOnline
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class HomeRepositoryImpl(
    private val api: Endpoints,
    private val dao: LocationDao
) :
    HomeRepository {

    override fun getAllLocations(): Flow<List<LocationEntity>> {
        return dao.getAllLocations()
    }

    override suspend fun getWeatherForLocation(id: Int): LocationEntity {

        var data: LocationEntity
        withContext(Dispatchers.IO)
        {
            if (isOnline(WeatherApp.getApp())) {
                data = dao.getSingleLocation(id)
                val weather = api.fetchCurrentWeather(
                    data.lat,
                    data.lng,
                    data.locationName,
                    BuildConfig.API_KEY,
                    UNIT_TYPE
                )
                data.lastUpdated = getCurrentTimestamp()

                weather.body()?.let {
                    it.coord.notNull { data.lat = it.lat ?: 0.0 }
                    it.coord.notNull { data.lng = it.lon ?: 0.0 }
                    it.main?.temp.also { t -> data.temp = t ?: 0.1 }
                    it.main?.tempMin.also { t -> data.min = t ?: 0.2 }
                    it.main?.tempMax.also { t -> data.max = t ?: 0.3 }
                    it.weather[0].description.also { t -> data.description = t ?: "" }
                    it.weather[0].icon.also { t -> data.condition = t.toString() }
                }

                dao.updateLocation(data)
            } else {
                data = dao.getSingleLocation(id)
            }
        }
        return data

    }

    override suspend fun getForecastForLocation(id: Int): Forecast {
        var forecast = Forecast()
        var data: LocationEntity
        withContext(Dispatchers.IO)
        {
            if (isOnline(WeatherApp.getApp())) {
                data = dao.getSingleLocation(id)
                val weather = api.fetchForecastWeather(
                    data.lat,
                    data.lng,
                    data.locationName,
                    BuildConfig.API_KEY,
                    UNIT_TYPE
                )

                data.lastUpdated = getCurrentTimestamp()
                weather.body().notNull { forecast = Forecast(it.list) }
                data.foreCastData = Gson().toJson(forecast)
                dao.updateLocation(data)
            } else {
                data = dao.getSingleLocation(id)
                data.notNull {
                    if (!data.foreCastData.isNullOrEmpty())
                        forecast = Gson().fromJson(data.foreCastData, Forecast::class.java)
                }
            }
        }
        return forecast

    }

}