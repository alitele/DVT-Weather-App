package com.ciklum.weatherapp.network.api

import retrofit2.Response

data class ApiResult<R>(
    var data: R? = null,
    var response: Response<R>? = null,
    var exception: Exception? = null
) {

    var isSuccess = exception == null
}
