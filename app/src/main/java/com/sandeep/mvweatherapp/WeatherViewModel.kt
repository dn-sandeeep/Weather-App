package com.sandeep.mvweatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sandeep.mvweatherapp.response.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherGetRequest) : ViewModel(){
    private val weatherU = MutableStateFlow<WeatherResponse?>(null)          //unchangable
    val weather: StateFlow<WeatherResponse?> = weatherU

    private val loading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = loading

    fun fetchWeather(city: String){
        viewModelScope.launch {
            loading.value = true
            try {
                val result = repository.getWeather(city)
                weatherU.value = result
            }catch (e: Exception){
                e.printStackTrace()
            }finally {
                loading.value = false
            }
        }
    }
}