package com.app.escaapp.ui.location


import android.content.Context.LOCATION_SERVICE
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_location.view.*

/**
 * A simple [Fragment] subclass.
 */
class LocationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.trackLocation.text = "Gps Run"
        getLocation(view)
    }

    private var location_service : LocationManager? = null
    private  var locationGps : Location? = null
    private  var locationNetwork : Location? = null

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
                    view.type_Location.text = "location from Network"
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
                    view.type_Location.text = "Not provider"
            }

        }
        catch(ex: SecurityException) { }

    }


}
