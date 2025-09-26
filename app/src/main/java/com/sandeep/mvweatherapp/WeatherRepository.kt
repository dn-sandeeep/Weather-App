package com.sandeep.mvweatherapp

import com.sandeep.mvweatherapp.response.WeatherResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

class WeatherGetRequest(
    private val client: HttpClient
){
    private val apiKey = "Enter your key"

    suspend fun getWeather(city: String): WeatherResponse {
        val url = "https://api.weatherapi.com/v1/current.json?key=$apiKey&q=$city"

        return client.get(url).body()
    }
}