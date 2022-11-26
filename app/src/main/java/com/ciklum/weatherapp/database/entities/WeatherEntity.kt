package com.ciklum.weatherapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Weather")
class WeatherEntity {

    @field:SerializedName("id")
    @PrimaryKey
    var id: Long = 0

    var name: String = ""

    var temperature: String = ""

    var min: String = ""

    var max: String = ""

    var condition: String = ""
}
