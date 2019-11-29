package com.app.escaapp.ui.location


import android.app.AlertDialog
import android.content.Context
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.vvalidator.form
import com.app.escaapp.NavBar
import com.app.escaapp.R
import com.app.escaapp.db.DB_saveLocattion
import com.app.escaapp.db.LocationModel
import com.app.escaapp.ui.manage.UserAdapter
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import com.google.android.gms.location.*
import com.google.android.gms.maps.GoogleMap
import kotlinx.android.synthetic.main.field.view.*
//import kotlinx.android.synthetic.main.field.view.delete_button
import kotlinx.android.synthetic.main.fragment_location_addlocation.view.*
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.*
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.btn_Add
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.btn_Cancel
import kotlinx.android.synthetic.main.pop_cancel_confirm.view.*

/**
 * A simple [Fragment] subclass.
 */
class LocationFragment : Fragment() /*, OnMapReadyCallback*/ {

    private lateinit var db: DB_saveLocattion
    private lateinit var mMap: GoogleMap
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mCurrentLocation: Location
    private lateinit var mSettingsClient: SettingsClient
    private lateinit var mLocationCallback: LocationCallback
    private lateinit var mLocationRequest: LocationRequest
    private lateinit var mLocationSettingsRequest: LocationSettingsRequest
    private var parentLinearLayout: LinearLayout? = null
    private var edtBtnEnb = true
    private var latestID = 0

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



    lateinit var locationAdapter : LocationAdapter

    private var edit_mode = false
    private val buffer = ArrayList<LocationModel>()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_location, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val spName = "Location"
        val sp = activity!!.getSharedPreferences(spName, Context.MODE_PRIVATE)
        //parentLinearLayout = view.findViewById<View>(R.id.parent_linear_layout) as LinearLayout
        //view.findViewById<View>(R.id.delete_button).visibility = View.GONE
        db = DB_saveLocattion(context!!)

        locationAdapter = initRecycleView(view)
        Edit_state(view)

        //db.deleteTable()
        // var x = 3
        //var temp1 = db.getLocationAll()
        //parentLinearLayout!!.addView(view.findViewById(R.id.first_row), parentLinearLayout!!.childCount)
        // mFusedLocationClient = activity?.let { LocationServices.getFusedLocationProviderClient(it) }!!
        // mSettingsClient = LocationServices.getSettingsClient(activity!!)

        NavBar().setGo(1, view)
       // viewInit(view)
        //btnListener(view)

        // Kick off the process of building the LocationCallback, LocationRequest, and
        // LocationSettingsRequest objects.

        //createLocationCallback()
        // createLocationRequest()
        // buildLocationSettingsRequest()
        //  startLocationUpdates()
        //fetchLocation()
        /*var mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        if(mapFragment == null){
            val fm = fragmentManager
            val ft = fm!!.beginTransaction()
            mapFragment = SupportMapFragment.newInstance()
            ft.replace(R.id.map, mapFragment).commit()
        }
        mapFragment?.getMapAsync(this)*/


        //view.trackLocation.text = "Gps Run"
        //getLocation(view)
    }

    private fun initRecycleView(view:View): LocationAdapter {
        //Toast.makeText(activity," user :: ${db.getAllCustom()}", Toast.LENGTH_LONG).show()
        val Adapter_ =  LocationAdapter(requireActivity())
        Adapter_.insertItem(db.getLocationAll())
        view.userListView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = Adapter_
        }
        return Adapter_
    }

    private fun disChange(view : View){
        locationAdapter.run{
            this.edit_mode = false
            restoreOldList()
            notifyDataSetChanged()
        }

        buffer.clear()
        End_Anime(view)
    }

    private fun discardChange(view :View) {
        val popView = LayoutInflater.from(requireContext()).inflate(R.layout.discard_change, null)
        val dialog = AlertDialog.Builder(requireContext())
                .setView(popView)
                .create()
        dialog.show()

        popView.run{
            No.setOnClickListener { dialog.dismiss() }
            Yes.setOnClickListener {
                disChange(view)
                dialog.dismiss()
            }
        }
    }

    private fun Edit_state(view:View){

        view.Edit.setOnClickListener {
            Start_Anime(view)
            locationAdapter.run{
                edit_mode = true
                backupOldList()
                notifyDataSetChanged()
            }
            buffer.clear()
        }

        view.Cancel.setOnClickListener{
            discardChange(view)
        }

        view.Done.setOnClickListener{
            db.run {
                deleteAllLocation(locationAdapter.getDelete())
                addAllLocation(buffer)
            }

            locationAdapter.run{
                this.edit_mode  = false
                insertItem(db.getLocationAll())
            }
            End_Anime(view)
            buffer.clear()
        }

        view.Add.setOnClickListener {
            val addUserDialog = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_location_addlocation,null)
            val addBuilder = AlertDialog.Builder(requireContext())
                    .setView(addUserDialog)
                    .create()

            addBuilder.show()
            addContactData(addUserDialog,addBuilder)
        }
    }

    private fun addContactData(view :View, builder: AlertDialog) {
        form{
            input(view.location_name){
                isNotEmpty().description("Please Input Contact Name ")
                length().atLeast(2)
                length().atMost(50)
            }


            view.btn_Cancel.setOnClickListener {
                val exitDialog = LayoutInflater.from(requireContext()).inflate(R.layout.pop_cancel_confirm,null)
                val exitBuild = AlertDialog.Builder(requireContext())
                        .setView(exitDialog)
                        .create()

                exitBuild.show()

                exitDialog.Yes.setOnClickListener{
                    exitBuild.dismiss()
                    builder.dismiss()
                }

                exitDialog.No.setOnClickListener{
                    exitBuild.dismiss()
                }
            }

            submitWith(view.btn_Add){
                try{
                    val user = LocationModel(
                            0,
                            view.location_name.text.toString(),
                            0.00,
                            0.00)
                    buffer.add(user)
                    locationAdapter.addItem(user)
                    Log.d("adapter","buffer check -> $buffer")
                    builder.dismiss()
                }catch (e : Exception){error(e) }
            }
        }
    }
    private fun Start_Anime(view:View){
        val fade = AnimationUtils.loadAnimation(requireContext(), R.anim.fade)
        val sd = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down)
        val sd_add = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in)

        view.run{
            block.startAnimation(sd)
            block.translationY = 30F

            Add.startAnimation(sd_add)
            Done.startAnimation(sd_add)
            Cancel.startAnimation(sd_add)

            Edit.startAnimation(fade)

            Cancel.visibility =  View.VISIBLE
            Done.visibility =  View.VISIBLE
            Add.visibility = View.VISIBLE

            Edit.visibility = View.INVISIBLE
        }


    }

    private fun End_Anime(view: View){

        val sd = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
        val sd_add = AnimationUtils.loadAnimation(activity,R.anim.fade_out)
        view.run{
            block.translationY = 0F
            block.startAnimation(sd)

            Add.startAnimation(sd_add)
            Cancel.startAnimation(sd_add)
            Done.startAnimation(sd_add)

            Edit.visibility = View.VISIBLE
            Cancel.visibility = View.INVISIBLE
            Done.visibility = View.INVISIBLE
            Add.visibility = View.GONE
        }
    }

   /* private fun btnListener(view: View) {
        view.findViewById<View>(R.id.edtBtn).setOnClickListener {
            if (edtBtnEnb) {
                view!!.findViewById<View>(R.id.add_field_button).visibility = View.VISIBLE
                //parentLinearLayout!!.getChildAt(0).edit_text.isEnabled = true
                view.findViewById<View>(R.id.edit_location_first).visibility = View.VISIBLE
                for (i in 0 until parentLinearLayout!!.childCount) {
                    parentLinearLayout!!.getChildAt(i).edit_text.isEnabled = true
                    parentLinearLayout!!.getChildAt(i).delete_button.visibility = View.VISIBLE
                    parentLinearLayout!!.getChildAt(i).edit_location.visibility = View.VISIBLE
                }
                view.findViewById<Button>(R.id.edtBtn).text = "save"
                edtBtnEnb = false
            } else {
                view!!.findViewById<View>(R.id.add_field_button).visibility = View.GONE
                view.findViewById<View>(R.id.edit_location_first).visibility = View.GONE
                for (i in 0 until parentLinearLayout!!.childCount) {
                    // var temp1 = db.getLocationAll()
                    db.updateLocation(LocationModel(parentLinearLayout!!.getChildAt(i).id, parentLinearLayout!!.getChildAt(i).edit_text.text.toString(), 0.01, 0.01), true, false, false)
                    parentLinearLayout!!.getChildAt(i).edit_text.isEnabled = false
                    parentLinearLayout!!.getChildAt(i).delete_button.visibility = View.GONE
                    parentLinearLayout!!.getChildAt(i).edit_location.visibility = View.GONE
                }
                view.findViewById<Button>(R.id.edtBtn).text = "edit"
                edtBtnEnb = true
            }

        }
        view.findViewById<View>(R.id.add_field_button).setOnClickListener {
            val inflater =
                    activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView: View = inflater.inflate(R.layout.field, null)

            //rowView.delete_button.visibility = View.GONE

            // Add the new row before the add field button.
            //var x =parentLinearLayout!!.childCount

            db.addLocation(LocationModel(latestID, "", 0.00, 0.00))
            var xxxx = latestID
            rowView.id = latestID++
            rowView.delete_button.setOnClickListener {
                db.deleteLocation(rowView.id.toString())
                parentLinearLayout!!.removeView(rowView)
            }

            parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount)
        }
    }

    private fun viewInit(view: View) {
        view.findViewById<View>(R.id.edit_text_first).isEnabled = false
        (view.findViewById<View>(R.id.edit_text_first) as EditText).setText("test")
        view.findViewById<View>(R.id.edit_location_first).visibility = View.GONE
        view.findViewById<View>(R.id.add_field_button).visibility = View.GONE
        //db.addLocation(LocationModel(-1,"testgetfromdb",0.00,0.00))
        //  db.deleteTable()
        //var i =db.getLocationAll().size
        for (item in db.getLocationAll()) {
            val inflater =
                    activity!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val rowView: View = inflater.inflate(R.layout.field, null)

            //rowView.delete_button.visibility = View.GONE
            rowView.delete_button.visibility = View.GONE
            rowView.edit_location.visibility = View.GONE
            rowView.edit_text.isEnabled = false
            // Add the new row before the add field button.
            rowView.edit_text.setText(item.name)
            rowView.id = item.id
            latestID = item.id+1
            rowView.delete_button.setOnClickListener {
                println(rowView.id.toString())
                db.deleteLocation(rowView.id.toString())
                parentLinearLayout!!.removeView(rowView)
            }

            parentLinearLayout!!.addView(rowView, parentLinearLayout!!.childCount)
        }

    }*/
    /* private fun createLocationCallback() {
         mLocationCallback = object : LocationCallback() {
             override fun onLocationResult(locationResult: LocationResult) {
                 super.onLocationResult(locationResult)
                 if(locationResult != null){
                     mCurrentLocation = locationResult.lastLocation
                     val mPosition = LatLng(mCurrentLocation.latitude, mCurrentLocation.longitude)

                     //mMap.addMarker(MarkerOptions().position(mPosition).title("Marker in Sydney"))
                     //mMap.moveCamera(CameraUpdateFactory.newLatLng(mPosition))
                     //mMap.moveCamera(CameraUpdateFactory.newLatLng(mPosition))
                     //mLastUpdateTime = DateFormat.getTimeInstance().format(Date())
                     // updateLocationUI()
                 }
             }
         }
     }*/

    /*private fun createLocationRequest() {
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
    }*/

    /* private fun startLocationUpdates() { // Begin by checking if the device has the necessary location settings.
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
     }*/

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

    /*override fun onMapReady(googleMap: GoogleMap) {

            mMap = googleMap
            mMap.uiSettings.isZoomControlsEnabled = true
            mMap.isMyLocationEnabled = true

            // Add a marker in Sydney and move the camera
            //val sydney = LatLng(9.0, 0.0)
            //mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

    }*/


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
