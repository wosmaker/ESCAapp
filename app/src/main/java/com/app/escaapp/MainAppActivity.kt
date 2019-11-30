package com.app.escaapp

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.*
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager

import com.app.escaapp.ui.emergency.EmergencyFragment
import com.app.escaapp.ui.location.FetchAddressIntentService
import com.app.escaapp.ui.location.LocationFragment
import com.app.escaapp.ui.location.LocationUpdatesService
import com.app.escaapp.ui.location.Utils
import com.app.escaapp.ui.manage.ManageFragment
import com.app.escaapp.ui.profile.ProfileFragment
import com.app.escaapp.ui.setting.SettingFragment
import com.google.android.material.snackbar.Snackbar

import kotlinx.android.synthetic.main.navbar_botton.*


class MainAppActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    // Used in checking for runtime permissions.
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34

    // The BroadcastReceiver used to listen from broadcasts from the service.
    private lateinit var myReceiver: MyReceiver

    // A reference to the service used to get location updates.
    private var mService: LocationUpdatesService? = null

    private var lastLocation: Location? = null
    private lateinit var resultReceiver: AddressResultReceiver
    // Tracks the bound state of the service.
    private var mBound = false
    private var first = true
    // UI elements.
    private var mRequestLocationUpdatesButton: Button? = null
    private var mRemoveLocationUpdatesButton: Button? = null
    private var fetchFinish = true
    // Monitors the state of the connection to the service.
    private lateinit var spName2: String
    private lateinit var sp2: SharedPreferences
    private lateinit var editor2: SharedPreferences.Editor
    private lateinit var location: Location

    private val mServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            val binder = service as LocationUpdatesService.LocalBinder
            mService = binder.getService()
            mBound = true
            mService?.requestLocationUpdates()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            mService = null
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        val spName = "App_config"
        val sp: SharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()

        spName2 = "location"
        sp2 = getSharedPreferences(spName2, Context.MODE_PRIVATE)
        editor2 = sp2.edit()

        permissionAsk()


        // Check that the user hasn't revoked permissions by going to Settings.

        if (Utils.requestingLocationUpdates(this)) {
            if (!checkPermissions()) {
                requestPermissions()
            }
        }
        bindService(Intent(this, LocationUpdatesService::class.java), mServiceConnection,
                Context.BIND_AUTO_CREATE)

        myReceiver = MyReceiver()
        resultReceiver = AddressResultReceiver(Handler())
        if (!checkPermissions()) {
            requestPermissions()
        } else {
            mService?.requestLocationUpdates()
        }
        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                IntentFilter(LocationUpdatesService.ACTION_BROADCAST))
    }

    override fun onResume() {
        super.onResume()
        bindService(Intent(this, LocationUpdatesService::class.java), mServiceConnection,
        Context.BIND_AUTO_CREATE)

        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                IntentFilter(LocationUpdatesService.ACTION_BROADCAST))
    }

    override fun onPause() {

         LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)

        super.onPause()
    }

    override fun onStop() {
        if (mBound) { // Unbind from the service. This signals to the service that this activity is no longer
// in the foreground, and the service can respond by promoting itself to a foreground
// service.
            unbindService(mServiceConnection)
            mBound = false
        }
        //LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)
//        PreferenceManager.getDefaultSharedPreferences(this)
//                .unregisterOnSharedPreferenceChangeListener(this)
        super.onStop()
    }

    private fun checkPermissions(): Boolean {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
    }

    private fun requestPermissions() {
        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
        // Provide an additional rationale to the user. This would happen if the user denied the
// request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.")
            Snackbar.make(
                    findViewById(R.id.master),
                    R.string.permission_rationale,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.ok) {
                        // Request permission
                        ActivityCompat.requestPermissions(this@MainAppActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                                REQUEST_PERMISSIONS_REQUEST_CODE)
                    }
                    .show()
        } else {
            Log.i(TAG, "Requesting permission")
            // Request permission. It's possible this can be auto answered if device policy
// sets the permission in a given state or the user denied the permission
// previously and checked "Never ask again".
            ActivityCompat.requestPermissions(this@MainAppActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
                                            grantResults: IntArray) {
        Log.i(TAG, "onRequestPermissionResult")
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.isEmpty()) { // If user interaction was interrupted, the permission request is cancelled and you
// receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.")
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Permission was granted.
                mService!!.requestLocationUpdates()
            } else { // Permission denied.
                // setButtonsState(false)
                Snackbar.make(
                        findViewById(R.id.master),
                        R.string.permission_denied_explanation,
                        Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.settings) {
                            // Build intent that displays the App settings screen.
                            val intent = Intent()
                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                            val uri = Uri.fromParts("package",
                                    BuildConfig.APPLICATION_ID, null)
                            intent.data = uri
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                            startActivity(intent)
                        }
                        .show()
            }
        }
    }

    /**
     * Receiver for broadcasts sent by [LocationUpdatesService].
     */
    private fun fetchAddress(location: Location) {


        if (!Geocoder.isPresent()) {
            Toast.makeText(this@MainAppActivity,
                    "no geoorder",
                    Toast.LENGTH_LONG).show()
            return
        }
        // Start service  to reflect new location
        startIntentService(location)
    }

    inner class MyReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            location = intent!!.getParcelableExtra<Location>(LocationUpdatesService.EXTRA_LOCATION)

            if (location != null && fetchFinish) {
                fetchFinish = false
                this@MainAppActivity.fetchAddress(location)
            }
        }
    }

    private fun startIntentService(location: Location) {

        val intent = Intent(this, FetchAddressIntentService::class.java).apply {
            putExtra(FetchAddressIntentService.Constants.RECEIVER, resultReceiver)
            putExtra(FetchAddressIntentService.Constants.LOCATION_DATA_EXTRA, location)
        }
        startService(intent)
    }

    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {

        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {

            // Display the address string
            // or an error message sent from the intent service.
            var addressOutput = resultData?.getString(FetchAddressIntentService.Constants.RESULT_DATA_KEY)
                    ?: ""

            // Show a toast message if an address was found.
            if (resultCode == FetchAddressIntentService.Constants.SUCCESS_RESULT) {
                Toast.makeText(this@MainAppActivity, addressOutput,
                        Toast.LENGTH_SHORT).show()
                editor2.putFloat("latitude",location.latitude.toFloat()).commit()
                editor2.putFloat("longitude",location.longitude.toFloat()).commit()
                editor2.putString("location",addressOutput).commit()
            }
            fetchFinish = true

        }

    }

    private fun permissionAsk() {
        ActivityCompat.requestPermissions(this,
                arrayOf(
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECEIVE_SMS,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION
                ), 12)
    }


}
