package com.ciklum.weatherapp.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "Location",indices = [Index(value = ["locationName"], unique = true)],)
@Parcelize
class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var lat: Double = 0.0,
    var lng: Double = 0.0,
    var min: Double = 0.0,
    var max: Double = 0.0,
    var temp: Double = 0.0,
    var condition: String = "",
    var description: String = "",
    var locationName: String = "",
    var isMyLocation: Boolean = false,
    var lastUpdated: Long = 0,
    var foreCastData: String = ""
) : Parcelable
