package com.ciklum.weatherapp.utils

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("code")
    val code: Int?,
    @SerializedName("details")
    val details: String,
    @SerializedName("message")
    val message: Any?
)