package com.innovative.porosh.tourmate.network

import com.innovative.porosh.tourmate.model.CurrentWeatherModel
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Url

const val weather_api_key = "e9260defcaa80ebeb476f4b91d6233d1"
const val base_url = "https://api.openweathermap.org/data/2.5/"

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

private val retrofit = Retrofit.Builder()
    .baseUrl(base_url)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .build()

interface WeatherServiceApi {
    @GET
    suspend fun getCurrentWeather(@Url endUrl: String): CurrentWeatherModel
}

object WeatherApi {
    val weatherServiceApi: WeatherServiceApi by lazy {
        retrofit.create(WeatherServiceApi::class.java)
    }
}