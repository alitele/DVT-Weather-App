package com.ciklum.weatherapp.extentions

import androidx.lifecycle.viewModelScope
import com.ciklum.weatherapp.BuildConfig
import com.ciklum.weatherapp.features.main.viewmodel.BaseViewModel
import com.ciklum.weatherapp.app.WeatherApp
import com.ciklum.weatherapp.features.main.viewmodel.BaseRepo
import com.ciklum.weatherapp.network.api.ApiResult
import com.ciklum.weatherapp.utils.ErrorResponse
import kotlinx.coroutines.*
import retrofit2.Response

suspend fun <R> BaseViewModel.doNetworkCall(block: suspend () -> Response<R>): Deferred<Response<R>> {
    return viewModelScope.async(coroutineContextProvider.IO) { block.invoke() }
}

suspend fun <R> Deferred<Response<R>>.awaitForResult(): ApiResult<R> {
    val result = ApiResult<R>()
    try {
        val response = await()

        val request = response.raw().request
        if ((response.code() == 401 || response.code() == 403) && request.url.toString() != BuildConfig.BASE_URL) {
            cancel("unauthorized")
        }

        result.response = response
        result.data = response.body()
    } catch (ex: Exception) {
        result.exception = ex
    } finally {
        return result
    }
}

fun <R> ApiResult<R>.onSuccess(block: (R) -> Unit): ApiResult<R> {
    if (this.isSuccess) {
        response?.body()?.notNull { block(it) }
    }
    return this
}

fun <R> ApiResult<R>.onError(block: (ErrorResponse) -> Unit) {
    if (!this.isSuccess) {
        block(
            ErrorResponse(
                this.response?.code(),
                "",
                WeatherApp.getApp().getString(com.ciklum.weatherapp.R.string.error_message)
            )
        )
    }
}

fun BaseViewModel.uiJob(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(coroutineContextProvider.Main) {
        block()
    }
}

fun BaseViewModel.ioJob(block: suspend CoroutineScope.() -> Unit): Job {
    return viewModelScope.launch(coroutineContextProvider.IO) {
        block()
    }
}

fun launchMain(block: suspend () -> Unit) {
    GlobalScope.launch(Dispatchers.Main) {
        block.invoke()
    }
}