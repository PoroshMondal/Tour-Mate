package com.innovative.porosh.tourmate.ui

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.location.GeofencingRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.innovative.porosh.tourmate.R
import com.innovative.porosh.tourmate.customDialogs.AddDestinationAlertDialog
import com.innovative.porosh.tourmate.prefs.lat
import com.innovative.porosh.tourmate.receivers.GeofencingBroadcastReceiver
import com.innovative.porosh.tourmate.userLocation.isLocationPermissionGranted
import com.innovative.porosh.tourmate.viewModels.LocationViewModel

class MapsFragment : Fragment() {

    private lateinit var map: GoogleMap
    private lateinit var geofencingClient: GeofencingClient
    private val locationViewModel: LocationViewModel by activityViewModels()
    private var geofenceList = mutableListOf<Geofence>()
    private val pendingIntent: PendingIntent by lazy {
        val intent = Intent(requireContext(),GeofencingBroadcastReceiver::class.java)
        PendingIntent.getBroadcast(requireContext(), 111, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private val callback = OnMapReadyCallback{ googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        map = googleMap
        if (isLocationPermissionGranted(requireActivity())) {
            map.isMyLocationEnabled = true
        }

        val latitude = locationViewModel.location.value!!.latitude
        val longitude = locationViewModel.location.value!!.longitude

        /*locationViewModel.location.observe(viewLifecycleOwner){
        }*/
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(latitude, longitude),15f))
        map.setOnMapLongClickListener {
            AddDestinationAlertDialog{
                createGeofence(LatLng(latitude,longitude), it)
            }.show(childFragmentManager,"destination")
        }

        showAlert("Set location alert","Long press anywhere on the map from which you want to get location alert")

        //val sydney = LatLng(-34.0, 151.0)
        //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun createGeofence(latlng: LatLng, place: String){
        val geofence = Geofence.Builder()
            .setCircularRegion(latlng.latitude, latlng.longitude, 200f)
            .setTransitionTypes(Geofence.GEOFENCE_TRANSITION_ENTER)
            .setExpirationDuration(6*60*60*1000)
            .setRequestId(place)
            .build()

        geofenceList.add(geofence)

        val request = GeofencingRequest.Builder()
            .addGeofences(geofenceList)
            .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER)
            .build()

        geofencingClient.addGeofences(request, pendingIntent)
            .addOnSuccessListener {
                Toast.makeText(activity,"Your place has been recored for next time alert",Toast.LENGTH_LONG).show()
            }.addOnFailureListener {

            }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        geofencingClient = LocationServices.getGeofencingClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    private fun showAlert(title: String, body: String){
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(title)
            .setMessage(body)
            .setPositiveButton("OK", null)
        val dialog = builder.create()
        dialog.show()
    }

}