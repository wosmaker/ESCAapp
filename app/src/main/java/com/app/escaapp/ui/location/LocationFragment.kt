package com.app.escaapp.ui.location


import android.Manifest
import android.content.IntentSender.SendIntentException
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.app.escaapp.R
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

import java.util.Timer
import kotlin.concurrent.schedule
/**
 * A simple [Fragment] subclass.
 */
class LocationFragment : Fragment() , OnMapReadyCallback {

    private lateinit var mMap : GoogleMap
    private lateinit var mFusedLocationClient : FusedLocationProviderClient
    private lateinit var mCurrentLocation : Location
    private lateinit var mSettingsClient: SettingsClient
    private lateinit var mLocationCallback: LocationCallback 
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationSettingsRequest: LocationSettingsRequest

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent
     * than this value.
     */
    companion object {
        private const val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 2000
        private const val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS =
            UPDATE_INTERVAL_IN_MILLISECONDS / 2
        private const val REQUEST_CHECK_SETTINGS = 0x1
    }

    
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        try {


            mFusedLocationClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
            mSettingsClient = LocationServices.getSettingsClient(activity!!)
            // Kick off the process of building the LocationCallback, LocationRequest, and
            // LocationSettingsRequest objects.

            createLocationCallback()
            createLocationRequest()
            buildLocationSettingsRequest()
            startLocationUpdates()
           //fetchLocation()
            var mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
            if(mapFragment == null){
                val fm = fragmentManager
                val ft = fm!!.beginTransaction()
                mapFragment = SupportMapFragment.newInstance()
                ft.replace(R.id.map, mapFragment).commit()
            }
            mapFragment?.getMapAsync(this)


        }
        catch (ex : NullPointerException){ }
        //view.trackLocation.text = "Gps Run"
        //getLocation(view)
    }


    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                if(locationResult != null){
                    mCurrentLocation = locationResult.lastLocation
                    val mPosition = LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude)

                    //mMap.addMarker(MarkerOptions().position(mPosition).title("Marker in Sydney"))
                    //mMap.moveCamera(CameraUpdateFactory.newLatLng(mPosition))
                    mMap.animateCamera(CameraUpdateFactory.newLatLng(mPosition))
                    //mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
                    // updateLocationUI()
                }
            }
        }
    }

    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    private fun buildLocationSettingsRequest() {
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest)
        mLocationSettingsRequest = builder.build()
    }

    private fun startLocationUpdates() { // Begin by checking if the device has the necessary location settings.
        mSettingsClient.checkLocationSettings(mLocationSettingsRequest)
            .addOnSuccessListener(activity!!) {

                mFusedLocationClient.requestLocationUpdates(
                    mLocationRequest,
                    mLocationCallback, Looper.myLooper()
                )
                //updateLocationUI()
            }
            .addOnFailureListener(activity!!) { e ->
                when ((e as ApiException).statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {

                        try { // Show the dialog by calling startResolutionForResult(), and check the
                            // result in onActivityResult().
                            val rae = e as ResolvableApiException
                            rae.startResolutionForResult(
                                activity!!,
                                REQUEST_CHECK_SETTINGS
                            )
                        } catch (sie: SendIntentException) {

                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        val errorMessage =
                            "Location settings are inadequate, and cannot be " +
                                    "fixed here. Fix in Settings."

                        Toast.makeText(activity, errorMessage, Toast.LENGTH_LONG)
                            .show()

                    }
                }
                //updateLocationUI()
            }
    }

    /*private fun updateLocationUI() {
        if (mCurrentLocation != null) {
            view!!.textView.text = mCurrentLocation.latitude as String
            view!!.textView4.text = mCurrentLocation.longitude as String
        }
    }*/

   /* private fun fetchLocation(){
        if(ContextCompat.checkSelfPermission(context!!,Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(context!!,Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),0x0000001)
            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),0x0000002)
            return
        }
        else{

        }

    }*/

   /* override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper())
    }*/

    override fun onMapReady(googleMap: GoogleMap) {

            mMap = googleMap
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.isMyLocationEnabled = true
            // Add a marker in Sydney and move the camera
            //val sydney = LatLng(9.0, 0.0)
            //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }


    /*private var location_service : LocationManager? = null
    private var locationGps : Location? = null
    private var locationNetwork : Location? = null

    private fun getLocation(view:View){
        location_service = activity!!.getSystemService(LOCATION_SERVICE) as LocationManager

        try {
            val hasGps = location_service!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val hasNetwork = location_service!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (hasGps || hasNetwork) {
                if (hasGps) {
                    location_service!!.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 0f, object : LocationListener {
                        override fun onLocationChanged(location: Location) {locationGps = location}
                        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                        override fun onProviderEnabled(provider: String) {}
                        override fun onProviderDisabled(provider: String) {}
                    })
                }

                if(hasNetwork){
                    location_service!!.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 2000, 0f, object : LocationListener {
                        override fun onLocationChanged(location: Location) {locationNetwork = location}
                        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}
                        override fun onProviderEnabled(provider: String) {}
                        override fun onProviderDisabled(provider: String) {}
                    })
                }

                locationGps = location_service!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                locationNetwork = location_service!!.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }

            if(locationGps!= null && locationNetwork!= null){
                if(locationGps!!.accuracy > locationNetwork!!.accuracy){
                    view.trackLocation.text = "Latitude >> ${locationGps!!.latitude} || Longgitude >> ${locationGps!!.longitude}"
                    view.type_Location.text = "Gps Location"
                }
                else{
                    view.trackLocation.text = "Latitude >> ${locationNetwork!!.latitude} || Longgitude >> ${locationNetwork!!.longitude}"
                    view.type_Location.text = "Network Location"
                }
            }
            else{
                if(locationGps!= null) {
                    view.trackLocation.text =
                        "Latitude >> ${locationGps!!.latitude} || Longgitude >> ${locationGps!!.longitude}"
                    view.type_Location.text = "Gps Location only"
                }
                else if(locationNetwork!= null) {
                    view.trackLocation.text =
                        "Latitude >> ${locationNetwork!!.latitude} || Longgitude >> ${locationNetwork!!.longitude}"
                    view.type_Location.text = "Network Location only"
                }
                else
                    view.type_Location.text = "No provider"
            }

        }
        catch(ex: SecurityException) { }

    }*/



}
