package com.app.escaapp

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.navbar_botton.view.*

class NavBar:Fragment() {

    fun nav_bar(args:Int,v:View){
        when (args) {
            0 -> v.nav_profile.setBackgroundResource(R.drawable.profile_home)
            1 -> v.nav_location.setBackgroundResource(R.drawable.location_home)
            2 -> v.nav_emergency.setBackgroundResource(R.drawable.emergency_home)
            3 -> v.nav_manage.setBackgroundResource(R.drawable.manage_home)
            4 -> v.nav_setting.setBackgroundResource(R.drawable.setting_home)
        }
        if(args != 0){v.nav_profile.setBackgroundResource(R.drawable.profile)}
        if(args != 1){v.nav_location.setBackgroundResource(R.drawable.location)}
        if(args != 2){v.nav_emergency.setBackgroundResource(R.drawable.emergency)}
        if(args != 3){v.nav_manage.setBackgroundResource(R.drawable.manage)}
        if(args != 4){v.nav_setting.setBackgroundResource(R.drawable.setting)}
    }

//    0 -> profile
//    1 -> location
//    2 -> emergency
//    3 -> manage
//    4 -> setting

    fun go(now:Int, des:Int,v:View){
        when(now){
            0 -> {
                // profile
                when(des){
                    0 -> {}
                    1 -> v.findNavController().navigate(R.id.action_profile_location)
                    2 -> v.findNavController().popBackStack()
                    3 -> v.findNavController().navigate(R.id.action_profile_manage)
                    4 -> v.findNavController().navigate(R.id.action_profile_setting)
                    }
            }
            1 -> {
                // location
                when(des){
                    0 -> v.findNavController().navigate(R.id.action_location_profile)
                    1 -> {}
                    2 -> v.findNavController().popBackStack()
                    3 -> v.findNavController().navigate(R.id.action_location_manage)
                    4 -> v.findNavController().navigate(R.id.action_location_setting)
                }
            }
            2 -> {
                when(des){
                    0 -> v.findNavController().navigate(R.id.action_emergency_profile)
                    1 -> v.findNavController().navigate(R.id.action_emergency_location)
                    2 -> {}
                    3 -> v.findNavController().navigate(R.id.action_emergency_manage)
                    4 -> v.findNavController().navigate(R.id.action_emergency_setting)
                }
            }
            3 -> {
                when(des){
                    0 -> v.findNavController().navigate(R.id.action_mange_profile)
                    1 -> v.findNavController().navigate(R.id.action_manage_location)
                    2 -> v.findNavController().popBackStack()
                    3 -> {}
                    4 -> v.findNavController().navigate(R.id.action_mange_setting)
                }
            }
            4 -> {
                when(des){
                    0 -> v.findNavController().navigate(R.id.action_setting_profile)
                    1 -> v.findNavController().navigate(R.id.action_setting_location)
                    2 -> v.findNavController().popBackStack()
                    3 -> v.findNavController().navigate(R.id.action_setting_manage)
                    4 -> {}
                }
            }
        }
    }

    fun setGo(now:Int, des:Int,v:View){
        when(now){
            0 -> {
                // profile
                when(des){
                    0 -> v.nav_profile.setOnClickListener{}
                    1 -> v.nav_location.setOnClickListener{v.findNavController().navigate(R.id.action_profile_location)}
                    2 -> v.nav_emergency.setOnClickListener{v.findNavController().popBackStack()}
                    3 -> v.nav_manage.setOnClickListener{v.findNavController().navigate(R.id.action_profile_manage)}
                    4 -> v.nav_setting.setOnClickListener{v.findNavController().navigate(R.id.action_profile_setting)}
                }
            }
            1 -> {
                // location
                when(des){
                    0 -> v.nav_profile.setOnClickListener{v.findNavController().navigate(R.id.action_location_profile)}
                    1 -> v.nav_location.setOnClickListener{}
                    2 -> v.nav_emergency.setOnClickListener{v.findNavController().popBackStack()}
                    3 -> v.nav_manage.setOnClickListener{v.findNavController().navigate(R.id.action_location_manage)}
                    4 -> v.nav_setting.setOnClickListener{v.findNavController().navigate(R.id.action_location_setting)}
                }
            }
            2 -> {
                when(des){
                    0 -> v.nav_profile.setOnClickListener{v.findNavController().navigate(R.id.action_emergency_profile)}
                    1 -> v.nav_location.setOnClickListener{v.findNavController().navigate(R.id.action_emergency_location)}
                    2 -> v.nav_emergency.setOnClickListener{}
                    3 -> v.nav_manage.setOnClickListener{v.findNavController().navigate(R.id.action_emergency_manage)}
                    4 -> v.nav_setting.setOnClickListener{v.findNavController().navigate(R.id.action_emergency_setting)}
                }
            }
            3 -> {
                when(des){
                    0 -> v.nav_profile.setOnClickListener{v.findNavController().navigate(R.id.action_mange_profile)}
                    1 -> v.nav_location.setOnClickListener{v.findNavController().navigate(R.id.action_manage_location)}
                    2 -> v.nav_emergency.setOnClickListener{v.findNavController().popBackStack()}
                    3 -> v.nav_manage.setOnClickListener{}
                    4 -> v.nav_setting.setOnClickListener{v.findNavController().navigate(R.id.action_mange_setting)}
                }
            }
            4 -> {
                when(des){
                    0 -> v.nav_profile.setOnClickListener{v.findNavController().navigate(R.id.action_setting_profile)}
                    1 -> v.nav_location.setOnClickListener{v.findNavController().navigate(R.id.action_setting_location)}
                    2 -> v.nav_emergency.setOnClickListener{v.findNavController().popBackStack()}
                    3 -> v.nav_manage.setOnClickListener{v.findNavController().navigate(R.id.action_setting_manage)}
                    4 -> v.nav_setting.setOnClickListener{}
                }
            }
        }
    }




}