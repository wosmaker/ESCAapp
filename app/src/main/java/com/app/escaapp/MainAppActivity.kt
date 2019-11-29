package com.app.escaapp

import android.Manifest
import android.content.Context
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.app.escaapp.ui.emergency.EmergencyFragment
import com.app.escaapp.ui.location.LocationFragment
import com.app.escaapp.ui.manage.ManageFragment
import com.app.escaapp.ui.profile.ProfileFragment
import com.app.escaapp.ui.setting.SettingFragment

import kotlinx.android.synthetic.main.navbar_botton.*


class MainAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        val spName = "App_config"
        val sp: SharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()
        permissionAsk()
    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    private  fun permissionAsk(){
        ActivityCompat.requestPermissions(this,
            arrayOf(
                Manifest.permission.CALL_PHONE,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),12)
    }


}
