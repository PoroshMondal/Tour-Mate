package com.innovative.porosh.tourmate.viewModels

import android.location.Location
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.innovative.porosh.tourmate.model.CurrentWeatherModel
import com.innovative.porosh.tourmate.repos.WeatherRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class WeatherViewModel: ViewModel() {
    private val TAG = "WeatherViewModel"
    private val respository = WeatherRepository()
    private val _currentData = MutableLiveData<CurrentWeatherModel>()

    var tempStatus = false

    val current: LiveData<CurrentWeatherModel>
        get() = _currentData

    fun getWeatherData(location: Location) {
        viewModelScope.launch {
            try {
                _currentData.value = respository.fetchCurrentData(location, tempStatus)
            }catch (e: Exception){
                Log.e(TAG, e.localizedMessage)
            }
        }
    }

}