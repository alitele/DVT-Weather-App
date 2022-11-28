package com.ciklum.weatherapp.injection

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ciklum.weatherapp.BuildConfig
import com.ciklum.weatherapp.database.daos.LocationDao
import com.ciklum.weatherapp.features.addlocation.viewmodel.AddLocationViewModel
import com.ciklum.weatherapp.features.home.viewmodel.HomeViewModel
import com.ciklum.weatherapp.features.addlocation.mylocation.MyLocationViewModel
import com.ciklum.weatherapp.features.favourites.repository.FavouritesRepository
import com.ciklum.weatherapp.features.favourites.repository.FavouritesRepositoryImpl
import com.ciklum.weatherapp.features.favourites.viewmodel.FavouritesViewModel
import com.ciklum.weatherapp.features.main.viewmodel.MainViewModel
import com.ciklum.weatherapp.utils.AppConfigs
import com.google.gson.GsonBuilder
import dk.zibralabs.m2call.migo.datasource.InterceptorManager
import dk.zibralabs.m2call.migo.datasource.NoConnectionInterceptor
import com.ciklum.weatherapp.network.api.Endpoints
import com.ciklum.weatherapp.utils.CoroutineContextProvider
import com.ciklum.weatherapp.utils.preferences.WeatherPrefsImpl
import com.ciklum.weatherapp.database.db.WeatherDatabase
import com.ciklum.weatherapp.features.home.repository.HomeRepository
import com.ciklum.weatherapp.features.home.repository.HomeRepositoryImpl
import com.ciklum.weatherapp.features.main.repository.MainRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executors
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
    viewModel { HomeViewModel(repository = get()) }
    viewModel { AddLocationViewModel() }
    viewModel { FavouritesViewModel(repository = get()) }
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
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().serializeNulls().create()
                )
            )
            .build()
    }

    single { get<Retrofit> { parametersOf(BuildConfig.BASE_URL) }.create(Endpoints::class.java) }
}

val databaseModule = module {

    fun provideDatabase(application: Application): WeatherDatabase {
        return Room.databaseBuilder(application, WeatherDatabase::class.java, "weather")
            .fallbackToDestructiveMigration()
            .setQueryCallback(RoomDatabase.QueryCallback { sqlQuery, bindArgs ->
                println("SQLQuery: $sqlQuery SQL Args: $bindArgs")
            }, Executors.newSingleThreadExecutor())
            .build()
    }

    fun provideWeatherDao(database: WeatherDatabase): LocationDao {
        return database.weatherDao
    }

    single { provideDatabase(androidApplication()) }
    single { provideWeatherDao(get()) }
}

val repositoryModule = module {

    fun provideFavRepository(context: Context, dao: LocationDao): FavouritesRepository {
        return FavouritesRepositoryImpl(context, dao)
    }
    single { provideFavRepository(androidContext(), get()) }

    fun provideHomeRepository(api: Endpoints, context: Context, dao: LocationDao): HomeRepository {
        return HomeRepositoryImpl(api, context, dao)
    }
    single { provideHomeRepository(get(), androidContext(), get()) }

    fun provideMainRepository(context: Context, dao: LocationDao): MainRepository {
        return provideMainRepository(context, dao)
    }
    single { provideMainRepository(androidContext(), get()) }

}
