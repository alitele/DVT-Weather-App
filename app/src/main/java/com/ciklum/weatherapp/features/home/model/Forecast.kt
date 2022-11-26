package com.ciklum.weatherapp.features.home.model

import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("list") var list: ArrayList<Weather> = arrayListOf(),
    @SerializedName("cod") var cod: Int? = null
)
