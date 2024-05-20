package com.innovative.porosh.tourmate

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationView
import com.innovative.porosh.tourmate.databinding.ActivityMainBinding
import com.innovative.porosh.tourmate.userLocation.isLocationPermissionGranted
import com.innovative.porosh.tourmate.userLocation.requestLocationPermission
import com.innovative.porosh.tourmate.viewModels.LocationViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var client: FusedLocationProviderClient
    private val locationViewModel: LocationViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        client = LocationServices.getFusedLocationProviderClient(this)

        setSupportActionBar(binding.appBar.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main)
        val navController = navHostFragment!!.findNavController()

        // If we use <fragment> instead <FragmentContainerView> in xml
        //val navController =  findNavController(R.id.nav_host_fragment_content_main)

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_tour, R.id.nav_weather, R.id.nav_maps
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (isLocationPermissionGranted(this)){
            detectUserLocation()
        }else{
            requestLocationPermission(this)
        }

    }

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main_drawer_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}