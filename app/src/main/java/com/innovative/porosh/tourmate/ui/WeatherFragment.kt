package com.innovative.porosh.tourmate.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.innovative.porosh.tourmate.databinding.FragmentWeatherBinding
import com.innovative.porosh.tourmate.prefs.getTempStatus
import com.innovative.porosh.tourmate.prefs.setTempStatus
import com.innovative.porosh.tourmate.userLocation.isLocationPermissionGranted
import com.innovative.porosh.tourmate.userLocation.requestLocationPermission
import com.innovative.porosh.tourmate.viewModels.LocationViewModel
import com.innovative.porosh.tourmate.viewModels.WeatherViewModel

class WeatherFragment : Fragment() {

    private lateinit var binding: FragmentWeatherBinding
    private val locationViewModel: LocationViewModel by activityViewModels()
    private val weatherViewModel: WeatherViewModel by viewModels()
    private lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        preferences = requireActivity().getSharedPreferences("weather_prefs",Context.MODE_PRIVATE)
        binding = FragmentWeatherBinding.inflate(inflater,container,false)

        binding.tempSwitch.isChecked = getTempStatus(preferences)
        weatherViewModel.tempStatus = getTempStatus(preferences)

        locationViewModel.location.observe(viewLifecycleOwner){
            // here we will fetch the weather information using location(lat,long)
            weatherViewModel.getWeatherData(it)
        }

        weatherViewModel.current.observe(viewLifecycleOwner){
            binding.current = it
        }

        binding.tempSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            setTempStatus(isChecked, preferences.edit())
            weatherViewModel.tempStatus = isChecked
            weatherViewModel.getWeatherData(locationViewModel.location.value!!)
        }

        return binding.root
    }

}