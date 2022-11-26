package com.ciklum.weatherapp.injection

import android.content.Context
import com.ciklum.weatherapp.BuildConfig
import com.ciklum.weatherapp.features.addlocation.viewmodel.AddLocationViewModel
import com.ciklum.weatherapp.features.home.viewmodel.HomeViewModel
import com.ciklum.weatherapp.features.addlocation.mylocation.MyLocationViewModel
import com.ciklum.weatherapp.features.favourites.viewmodel.FavouritesViewModel
import com.ciklum.weatherapp.features.main.viewmodel.MainViewModel
import com.ciklum.weatherapp.utils.AppConfigs
import com.google.gson.GsonBuilder
import dk.zibralabs.m2call.migo.datasource.InterceptorManager
import dk.zibralabs.m2call.migo.datasource.NoConnectionInterceptor
import com.ciklum.weatherapp.network.api.Endpoints
import com.ciklum.weatherapp.utils.CoroutineContextProvider
import com.ciklum.weatherapp.utils.preferences.WeatherPrefsImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single {
        androidContext().getSharedPreferences(AppConfigs.PREFS_USER_DETAILS, Context.MODE_PRIVATE)
    }

    single { WeatherPrefsImpl(get()) }
    single { CoroutineContextProvider() }
}

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { HomeViewModel() }
    viewModel { AddLocationViewModel() }
    viewModel { FavouritesViewModel() }
    viewModel { MyLocationViewModel() }
}

val apiModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(InterceptorManager.commonInterceptor())
            .addInterceptor(NoConnectionInterceptor(androidContext()))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
    }

    factory { params ->
        Retrofit.Builder()
            .client(get())
            .baseUrl(params.get<String>(0))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .build()
    }

    single { get<Retrofit> { parametersOf(BuildConfig.BASE_URL) }.create(Endpoints::class.java) }
}
