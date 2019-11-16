package com.app.escaapp

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
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
        // first fragment page
        navbar_show(2)


        nav_profile.setOnClickListener {
            navbar_show(0)

            val profileFragment = ProfileFragment()

            // Get the support fragment manager instance
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.replace(R.id.nav_host_fragment,profileFragment)
            //.addToBackStack(null)
            transaction.commit()
        }

        nav_location.setOnClickListener {
            navbar_show(1)

            val locationFragment = LocationFragment()

            // Get the support fragment manager instance
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.replace(R.id.nav_host_fragment,locationFragment)
            //.addToBackStack(null)
            transaction.commit()

        }

        nav_emergency.setOnClickListener {
            navbar_show(2)

            val emergencyFragment = EmergencyFragment()

            // Get the support fragment manager instance
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.replace(R.id.nav_host_fragment,emergencyFragment)
            //.addToBackStack(null)
            transaction.commit()

        }

        nav_manage.setOnClickListener {
            navbar_show(3)
            val manageFragment = ManageFragment()

            // Get the support fragment manager instance
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.replace(R.id.nav_host_fragment,manageFragment)
            //.addToBackStack(null)
            transaction.commit()

        }

        nav_setting.setOnClickListener {
            navbar_show(4)

            val settingFragment = SettingFragment()
            val manager = supportFragmentManager
            val transaction = manager.beginTransaction()

            transaction.replace(R.id.nav_host_fragment, settingFragment)
            //transaction.addToBackStack(null)
            transaction.commit()
        }


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

}
