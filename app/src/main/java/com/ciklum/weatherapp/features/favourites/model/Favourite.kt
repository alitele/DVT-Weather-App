package com.ciklum.weatherapp.features.favourites.model

data class Favourite(val id: Int, val location: String) {

    fun areBothObjectsSame(oldItem: Favourite, newItem: Favourite): Boolean {
        if (oldItem.location != newItem.location) return false
        return true
    }
}
