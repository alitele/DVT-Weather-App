package com.ciklum.weatherapp.utils

import java.text.SimpleDateFormat
import java.util.*
object Helper {

    fun getDateString(time: Int?): String {
        val simpleDateFormat = SimpleDateFormat("EEE HH:mm", Locale.ENGLISH)
        return simpleDateFormat.format(time?.times(1000L) ?: 0)
    }

}