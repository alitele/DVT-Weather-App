package com.ciklum.weatherapp.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Favourite")
class FavouriteEntity {

    @field:SerializedName("id")
    @PrimaryKey
    var id: Long = 0

    var location: String = ""

}
