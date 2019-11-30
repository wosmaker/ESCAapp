package com.app.escaapp

import android.Manifest
import android.content.*
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.ResultReceiver
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.app.escaapp.ui.location.FetchAddressIntentService
import com.app.escaapp.ui.location.LocationUpdatesService
import com.app.escaapp.ui.location.Utils
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {
//    private val TAG = MainActivity::class.java.simpleName
//
//    // Used in checking for runtime permissions.
//    private val REQUEST_PERMISSIONS_REQUEST_CODE = 34
//
//    // The BroadcastReceiver used to listen from broadcasts from the service.
//    private lateinit var myReceiver: MyReceiver
//
//    // A reference to the service used to get location updates.
//    private var mService: LocationUpdatesService? = null
//
//    private var lastLocation: Location? = null
//    private lateinit var resultReceiver: AddressResultReceiver
//    // Tracks the bound state of the service.
//    private var mBound = false
//    private var first = true
//    // UI elements.
//    private var mRequestLocationUpdatesButton: Button? = null
//    private var mRemoveLocationUpdatesButton: Button? = null
//    private var fetchFinish = true
//    // Monitors the state of the connection to the service.
//    private val mServiceConnection = object : ServiceConnection {
//        override fun onServiceConnected(name: ComponentName, service: IBinder) {
//            val binder = service as LocationUpdatesService.LocalBinder
//            mService = binder.getService()
//            mBound = true
//            mService?.requestLocationUpdates()
//        }
//
//        override fun onServiceDisconnected(name: ComponentName) {
//            mService = null
//            mBound = false
//        }
//    }

    val LocationPermissionRequestCode = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        bindService(Intent(this, LocationUpdatesService::class.java), mServiceConnection,
//        Context.BIND_AUTO_CREATE)
//        // Check that the user hasn't revoked permissions by going to Settings.
//
//        if (Utils.requestingLocationUpdates(this)) {
//            if (!checkPermissions()) {
//                requestPermissions()
//            }
//        }
//        myReceiver = MyReceiver()
//        resultReceiver = AddressResultReceiver(Handler())
        val spName = "App_config"
        val sp: SharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()


        if (sp.getBoolean("FirstRun", true)) {
            editor.putBoolean("FirstRun", false)
            editor.commit()

            try {
                val users = initital()
                val db = UsersDBHelper(this)
                db.addAllUser(users)
            } catch (e: Exception) {
                Toast.makeText(this, "Error $e", Toast.LENGTH_LONG).show()
            }

            val intent = Intent(this, FirstTime::class.java)
            startActivity(intent)
        } else {
            val intent = Intent(this, MainAppActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() {
        super.onStart()

//        PreferenceManager.getDefaultSharedPreferences(this)
//                .registerOnSharedPreferenceChangeListener(this)

//        if (!checkPermissions()) {
//            requestPermissions()
//        } else {
//            mService?.requestLocationUpdates()
//        }
//        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
//                IntentFilter(LocationUpdatesService.ACTION_BROADCAST))
        // mRemoveLocationUpdatesButton.setOnClickListener(View.OnClickListener { mService.removeLocationUpdates() })
        // Restore the state of the buttons when the activity (re)launches.
        //setButtonsState(Utils.requestingLocationUpdates(this))
        // Bind to the service. If the service is in foreground mode, this signals to the service
// that since this activity is in the foreground, the service can exit foreground mode.

    }

//    override fun onResume() {
//        super.onResume()
//        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
//                IntentFilter(LocationUpdatesService.ACTION_BROADCAST))
//    }

//    override fun onPause() {
//
//       if(!first) LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)
//        first = false
//        super.onPause()
//    }

//    override fun onStop() {
//        if (mBound) { // Unbind from the service. This signals to the service that this activity is no longer
//// in the foreground, and the service can respond by promoting itself to a foreground
//// service.
//            unbindService(mServiceConnection)
//            mBound = false
//        }
//        //LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver)
////        PreferenceManager.getDefaultSharedPreferences(this)
////                .unregisterOnSharedPreferenceChangeListener(this)
//        super.onStop()
//    }

    /**
     * Returns the current state of the permissions needed.
     */
//    private fun checkPermissions(): Boolean {
//        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//    }
//
//    private fun requestPermissions() {
//        val shouldProvideRationale = ActivityCompat.shouldShowRequestPermissionRationale(this,
//                Manifest.permission.ACCESS_FINE_LOCATION)
//        // Provide an additional rationale to the user. This would happen if the user denied the
//// request previously, but didn't check the "Don't ask again" checkbox.
//        if (shouldProvideRationale) {
//            Log.i(TAG, "Displaying permission rationale to provide additional context.")
//            Snackbar.make(
//                    findViewById(R.id.master),
//                    R.string.permission_rationale,
//                    Snackbar.LENGTH_INDEFINITE)
//                    .setAction(R.string.ok) {
//                        // Request permission
//                        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                                REQUEST_PERMISSIONS_REQUEST_CODE)
//                    }
//                    .show()
//        } else {
//            Log.i(TAG, "Requesting permission")
//            // Request permission. It's possible this can be auto answered if device policy
//// sets the permission in a given state or the user denied the permission
//// previously and checked "Never ask again".
//            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                    REQUEST_PERMISSIONS_REQUEST_CODE)
//        }
//    }

    /**
     * Callback received when a permissions request has been completed.
     */
//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>,
//                                            grantResults: IntArray) {
//        Log.i(TAG, "onRequestPermissionResult")
//        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
//            if (grantResults.size <= 0) { // If user interaction was interrupted, the permission request is cancelled and you
//// receive empty arrays.
//                Log.i(TAG, "User interaction was cancelled.")
//            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) { // Permission was granted.
//                mService!!.requestLocationUpdates()
//            } else { // Permission denied.
//                // setButtonsState(false)
//                Snackbar.make(
//                        findViewById(R.id.master),
//                        R.string.permission_denied_explanation,
//                        Snackbar.LENGTH_INDEFINITE)
//                        .setAction(R.string.settings) {
//                            // Build intent that displays the App settings screen.
//                            val intent = Intent()
//                            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
//                            val uri = Uri.fromParts("package",
//                                    BuildConfig.APPLICATION_ID, null)
//                            intent.data = uri
//                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                            startActivity(intent)
//                        }
//                        .show()
//            }
//        }
//    }
//
//    /**
//     * Receiver for broadcasts sent by [LocationUpdatesService].
//     */
//    private fun fetchAddress(location: Location) {
//
//
//        if (!Geocoder.isPresent()) {
//            Toast.makeText(this@MainActivity,
//                    "no geoorder",
//                    Toast.LENGTH_LONG).show()
//            return
//        }
//        // Start service  to reflect new location
//        startIntentService(location)
//    }
//
//    inner class MyReceiver : BroadcastReceiver() {
//        override fun onReceive(context: Context?, intent: Intent?) {
//            var location = intent?.getParcelableExtra<Location>(LocationUpdatesService.EXTRA_LOCATION)
//
//            if (location != null&&fetchFinish) {
//                fetchFinish = false
//                this@MainActivity.fetchAddress(location)
//            }
//        }
//    }
//
//    private fun startIntentService(location : Location) {
//
//        val intent = Intent(this, FetchAddressIntentService::class.java).apply {
//            putExtra(FetchAddressIntentService.Constants.RECEIVER, resultReceiver)
//            putExtra(FetchAddressIntentService.Constants.LOCATION_DATA_EXTRA, location)
//        }
//        startService(intent)
//    }
//
//    internal inner class AddressResultReceiver(handler: Handler) : ResultReceiver(handler) {
//
//        override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
//
//            // Display the address string
//            // or an error message sent from the intent service.
//            var addressOutput = resultData?.getString(FetchAddressIntentService.Constants.RESULT_DATA_KEY)
//                    ?: ""
//
//            // Show a toast message if an address was found.
//            if (resultCode == FetchAddressIntentService.Constants.SUCCESS_RESULT) {
//                Toast.makeText(this@MainActivity, addressOutput,
//                        Toast.LENGTH_SHORT).show()
//            }
//            fetchFinish = true
//
//        }
//
//    }



    //    fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, s: String) { // Update the buttons state depending on whether location updates are being requested.
//        if (s == Utils.KEY_REQUESTING_LOCATION_UPDATES) {
//            setButtonsState(sharedPreferences.getBoolean(Utils.KEY_REQUESTING_LOCATION_UPDATES,
//                    false))
//        }
//    }
//
    fun initital(): ArrayList<UserModel> {
        val defaultCall = ArrayList<UserModel>()
        defaultCall.add(UserModel(0, "เหตุด่วน เหตุร้าย (เบอร์ฉุกเฉินแห่งชาติ)", "911", "", false))
        defaultCall.add(UserModel(0, "ศูนย์บริการข่าวสารข้อมูลและรับเรื่องร้องเรียน", "1599", "", false))
        defaultCall.add(UserModel(0, "ตำรวจท่องเที่ยว", "1155", "", false))
        defaultCall.add(UserModel(0, "ตำรวจทางหลวง", "1193", "", false))
        defaultCall.add(UserModel(0, "ตำรวจกองปราบ", "1195", "", false))
        defaultCall.add(UserModel(0, "ตำรวจจราจร ศูนย์ควบคุมและสั่งการจราจร", "1197", "", false))
        defaultCall.add(UserModel(0, "โรงพยาบาลตำรวจแ", "1691", "", false))
        defaultCall.add(UserModel(0, "ตำรวจตระเวนชายแดน", "1190", "", false))
        defaultCall.add(UserModel(0, "ตำรวจตรวจคนเข้าเมือง", "1178", "", false))
        defaultCall.add(UserModel(0, "ตำรวจน้ำ อุบัติเหตุทางน้ำ", "1196", "", false))
        defaultCall.add(UserModel(0, "ตำรวจรถไฟ", "1690", "", false))

        defaultCall.add(UserModel(0, "ศูนย์เตือนภัยพิบัติแห่งชาติ", "192", "", false))
        defaultCall.add(UserModel(0, "แจ้งอัคคีภัย สัตว์เข้าบ้าน สำนักป้องกันและบรรเทาสาธารณภัย", "1690", "", false))
        defaultCall.add(UserModel(0, "หน่วยแพทย์กู้ชีวิต", "1554", "", false))
        defaultCall.add(UserModel(0, "การท่องเที่ยวแห่งประเทศไทย", "1672", "", false))
        defaultCall.add(UserModel(0, "สถาบันการแพทย์ฉุกเฉินแห่งชาติ", "1669", "", false))
        defaultCall.add(UserModel(0, "ศูนย์เตือนภัยพิบัติแห่งชาติ", "1860", "", false))

        return defaultCall
    }
}
