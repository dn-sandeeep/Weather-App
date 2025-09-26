package com.sandeep.mvweatherapp.response
import kotlinx.serialization.Serializable
@Serializable
data class WeatherResponse(
    val current: Current,
    val location: Location
)