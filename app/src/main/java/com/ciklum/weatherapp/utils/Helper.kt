package com.ciklum.weatherapp.utils

import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.util.Log
import com.ciklum.weatherapp.app.WeatherApp
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.utils.preferences.AppResult
import com.google.gson.GsonBuilder
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Helper {

    fun getDateString(time: Int?): String {
        val simpleDateFormat = SimpleDateFormat("EEE HH:mm", Locale.ENGLISH)
        return simpleDateFormat.format(time?.times(1000L) ?: 0)
    }

    fun getCurrentTimestamp(): Long = Calendar.getInstance().timeInMillis

    fun geoCodeLocation(location: Location): String {
        return try {
            val geocoder = Geocoder(WeatherApp.getApp(), Locale.getDefault())
            val addresses: List<Address>? =
                geocoder.getFromLocation(location.latitude, location.longitude, 1)
            addresses?.get(0)?.locality ?: ""
        } catch (e: Exception) {
            "Unknown"
        }
    }

    fun getLocationFromAddress(strAddress: String?): Location? {
        val coder = Geocoder(WeatherApp.getApp())
        val address: MutableList<Address> =
            coder.getFromLocationName(strAddress!!, 1) ?: return null
        val singleAddress = address[0]
        val location = Location("")
        location.latitude = singleAddress.latitude
        location.longitude = singleAddress.longitude
        return location
    }

    fun <T : Any> handleApiError(resp: Response<T>): AppResult.Error {
        val error = parseError(resp)
        return AppResult.Error(Exception(error.message))
    }

    fun <T : Any> handleSuccess(response: Response<T>): AppResult<T> {
        response.body()?.let {
            return AppResult.Success(it)
        } ?: return handleApiError(response)
    }

    private fun parseError(response: Response<*>): APIError {

        val gson = GsonBuilder().create()
        val error: APIError

        try {
            error = gson.fromJson(response.errorBody()?.string(), APIError::class.java)
        } catch (e: IOException) {
            e.message?.let { }
            return APIError()
        }
        return error
    }

}