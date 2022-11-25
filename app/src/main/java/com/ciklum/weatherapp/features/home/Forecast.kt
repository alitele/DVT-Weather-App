package com.ciklum.weatherapp.features.home

data class Forecast(val day: String, val temperature: String, val condition: Int) {

    fun areBothObjectsSame(oldItem: Forecast, newItem: Forecast): Boolean {
        if (oldItem.day != newItem.day) return false
        if (oldItem.condition != newItem.condition) return false
        if (oldItem.temperature != newItem.temperature) return false
        return true
    }

}
