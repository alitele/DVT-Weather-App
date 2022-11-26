package com.ciklum.weatherapp.app

import android.app.Application
import com.ciklum.weatherapp.BuildConfig
import com.ciklum.weatherapp.injection.apiModule
import com.ciklum.weatherapp.injection.appModule
import com.ciklum.weatherapp.injection.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        app = this
        stopKoin()
        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            androidContext(this@WeatherApp)
            modules(listOf(appModule, viewModelModule, apiModule))
        }
    }

    companion object {
        private lateinit var app: WeatherApp
        fun getApp(): WeatherApp {
            return app
        }
    }
}