package com.innovative.porosh.tourmate.ui

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private lateinit var client: FusedLocationProviderClient
    private val locationViewModel: LocationViewModel by viewModels()
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
        client = LocationServices.getFusedLocationProviderClient(requireActivity())
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

        if (isLocationPermissionGranted(requireActivity())){
            detectUserLocation()
        }else{
            requestLocationPermission(requireActivity())
        }

        binding.tempSwitch.setOnCheckedChangeListener { compoundButton, isChecked ->
            setTempStatus(isChecked, preferences.edit())
            weatherViewModel.tempStatus = isChecked
            weatherViewModel.getWeatherData(locationViewModel.location.value!!)
        }

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 999){
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)){
                detectUserLocation()
            }else{
                // show a meaningful info in dialog
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun detectUserLocation() {
        client.lastLocation.addOnSuccessListener {
            locationViewModel.setNewLocation(it)
        }
    }

}