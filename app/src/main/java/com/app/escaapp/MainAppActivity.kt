package com.app.escaapp

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

import com.app.escaapp.ui.emergency.EmergencyFragment
import com.app.escaapp.ui.location.LocationFragment
import com.app.escaapp.ui.manage.ManageFragment
import com.app.escaapp.ui.profile.ProfileFragment
import com.app.escaapp.ui.setting.SettingFragment

import kotlinx.android.synthetic.main.activity_main_app.*
import kotlinx.android.synthetic.main.navbar_botton.*

class MainAppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_app)

        val spName = "App_config"
        val sp: SharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()

        permissionAsk()

        init()
        navbar_action()
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

    private fun navbar_show(args:Int){
        when(args){
            0 -> nav_profile.setBackgroundResource(R.drawable.profile_home)
            1 -> nav_location.setBackgroundResource(R.drawable.location_home)
            2 -> nav_emergency.setBackgroundResource(R.drawable.emergency_home)
            3 -> nav_manage.setBackgroundResource(R.drawable.manage_home)
            4 -> nav_setting.setBackgroundResource(R.drawable.setting_home)
        }

        if(args != 0){nav_profile.setBackgroundResource(R.drawable.profile)}
        if(args != 1){nav_location.setBackgroundResource(R.drawable.location)}
        if(args != 2){nav_emergency.setBackgroundResource(R.drawable.emergency)}
        if(args != 3){nav_manage.setBackgroundResource(R.drawable.manage)}
        if(args != 4){nav_setting.setBackgroundResource(R.drawable.setting)}

    }

    private fun init() {
        navbar_show(2)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment,EmergencyFragment())
            //.addToBackStack(null)
            .commit()
    }

    private fun navbar_action(){
        nav_profile.setOnClickListener {
            navbar_show(0)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment,ProfileFragment())
                //.addToBackStack(null)
                .commit()
        }

        nav_location.setOnClickListener {
            navbar_show(1)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, LocationFragment())
                //.addToBackStack(null)
                .commit()

        }

        nav_emergency.setOnClickListener {
            navbar_show(2)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment,EmergencyFragment())
                //.addToBackStack(null)
                .commit()
        }

        nav_manage.setOnClickListener {
            navbar_show(3)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment,ManageFragment())
                //.addToBackStack(null)
                .commit()

        }

        nav_setting.setOnClickListener {
            navbar_show(4)

            supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, SettingFragment())
                //.addToBackStack(null)
                .commit()
        }
    }


}
