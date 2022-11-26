package com.ciklum.weatherapp.features.home.model

import com.google.gson.annotations.SerializedName

data class Weather(
    @SerializedName("coord") var coord: Coord? = Coord(),
    @SerializedName("weather") var weather: ArrayList<Weather> = arrayListOf(),
    @SerializedName("main") var main: Main? = Main(),
    @SerializedName("visibility") var visibility: Int?,
    @SerializedName("wind") var wind: Wind? = Wind(),
    @SerializedName("rain") var rain: Rain? = Rain(),
    @SerializedName("dt_txt") var dt_txt: String?,
    @SerializedName("clouds") var clouds: Clouds? = Clouds(),
    @SerializedName("dt") var dt: Int? = null,
    @SerializedName("sys") var sys: Sys? = Sys(),
    @SerializedName("timezone") var timezone: Int?,
    @SerializedName("id") var id: Int?,
    @SerializedName("name") var name: String?,
    @SerializedName("cod") var cod: Int?
) {

    data class Coord(
        @SerializedName("lon") var lon: Double? = null,
        @SerializedName("lat") var lat: Double? = null
    )

    data class Weather(
        @SerializedName("id") var id: Int? = null,
        @SerializedName("main") var main: String? = null,
        @SerializedName("description") var description: String? = null,
        @SerializedName("icon") var icon: String? = null
    )

    data class Main(
        @SerializedName("temp") var temp: Double? = null,
        @SerializedName("feels_like") var feelsLike: Double? = null,
        @SerializedName("temp_min") var tempMin: Double? = null,
        @SerializedName("temp_max") var tempMax: Double? = null,
        @SerializedName("pressure") var pressure: Int? = null,
        @SerializedName("humidity") var humidity: Int? = null,
        @SerializedName("sea_level") var seaLevel: Int? = null,
        @SerializedName("grnd_level") var grndLevel: Int? = null
    )

    data class Wind(
        @SerializedName("speed") var speed: Double? = null,
        @SerializedName("deg") var deg: Int? = null,
        @SerializedName("gust") var gust: Double? = null
    )

    data class Rain(
        @SerializedName("1h") var oneHour: Double? = null
    )

    data class Clouds(
        @SerializedName("all") var all: Int? = null
    )


    data class Sys(
        @SerializedName("type") var type: Int? = null,
        @SerializedName("id") var id: Int? = null,
        @SerializedName("country") var country: String? = null,
        @SerializedName("sunrise") var sunrise: Int? = null,
        @SerializedName("sunset") var sunset: Int? = null
    )

}