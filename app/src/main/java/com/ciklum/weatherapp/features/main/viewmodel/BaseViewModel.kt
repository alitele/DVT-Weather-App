package com.ciklum.weatherapp.features.main.viewmodel

import androidx.lifecycle.ViewModel
import com.ciklum.weatherapp.utils.CoroutineContextProvider
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@OptIn(KoinApiExtension::class)
abstract class BaseViewModel : ViewModel(), KoinComponent {
    val coroutineContextProvider by inject<CoroutineContextProvider>()
}