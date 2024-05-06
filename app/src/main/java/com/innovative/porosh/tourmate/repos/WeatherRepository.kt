package com.innovative.porosh.tourmate.repos

import android.location.Location
import com.innovative.porosh.tourmate.model.CurrentWeatherModel
import com.innovative.porosh.tourmate.network.WeatherApi
import com.innovative.porosh.tourmate.network.weather_api_key

class WeatherRepository {

    suspend fun fetchCurrentData(location: Location, tempStatus: Boolean): CurrentWeatherModel?{
        val endUrl = "weather?lat=${location.latitude}&lon=${location.longitude}&units=metric&appid=$weather_api_key"
        val currentWeatherModel = WeatherApi.weatherServiceApi.getCurrentWeather(endUrl)
        return currentWeatherModel
    }

}