package com.ciklum.weatherapp.extentions

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.ciklum.weatherapp.database.entities.LocationEntity
import com.ciklum.weatherapp.features.main.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@SuppressLint("ObsoleteSdkInt")
fun AppCompatActivity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }

            statusBarColor = Color.TRANSPARENT
        }
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

fun LocationEntity.isNull(): Boolean {
    return this == null
}

fun List<LocationEntity>.isNullOrEmpty(): Boolean {
    return this == null || this.isEmpty()
}

fun <T> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}