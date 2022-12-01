package com.ciklum.weatherapp.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices

class GeoLocationHelper(context: Context) {
    private val context: Context = context
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private var locationRequest = LocationRequest()
    private var startedLocationTracking = false

    init {
        configureLocationRequest()
        setupLocationProviderClient()
    }

    private fun setupLocationProviderClient() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    }

    private fun configureLocationRequest() {
        locationRequest.interval = UPDATE_INTERVAL_MILLISECONDS
        locationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_MILLISECONDS
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    @SuppressLint("MissingPermission")
    fun startLocationTracking(
        locationCallback: LocationCallback,
        lastLocationCallback: LastLocationCallback
    ) {
        if (!startedLocationTracking) {
            //noinspection MissingPermission

            fusedLocationClient.lastLocation.addOnSuccessListener {
                lastLocationCallback.lastLocationCallback(it)
            }

            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
            this.locationCallback = locationCallback
            startedLocationTracking = true
        }
    }

    fun stopLocationTracking() {
        if (startedLocationTracking) {
            fusedLocationClient.removeLocationUpdates(locationCallback)
        }
    }

    companion object {
        const val UPDATE_INTERVAL_MILLISECONDS: Long = 1000 * 60
        const val FASTEST_UPDATE_INTERVAL_MILLISECONDS = UPDATE_INTERVAL_MILLISECONDS / 2
    }

    interface LastLocationCallback {
        fun lastLocationCallback(location: Location)
    }
}