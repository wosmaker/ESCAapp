package com.app.escaapp.ui.setting

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.app.escaapp.NavBar
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_setting.view.*
import java.util.jar.Manifest


class SettingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_setting, container, false)
    }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         val spName = "App_config"
         val sp = activity!!.getSharedPreferences(spName, Context.MODE_PRIVATE)
         NavBar().setGo(4,view)

         setting_begin(view,sp)
         setting_listen(view,sp)
     }


    fun setting_listen(view:View , sp:SharedPreferences){
        val editor = sp.edit()
        view.run{
            sw_gpsTrack.setOnCheckedChangeListener { _, isChecked ->  editor.putBoolean("gpsTrack",isChecked).commit()}
            sw_gpsServer.setOnCheckedChangeListener{_,isChecked -> editor.putBoolean("gpsServer",isChecked).commit()}
            sw_gpsSMS.setOnCheckedChangeListener{_,isChecked -> editor.putBoolean("gpsSMS",isChecked).commit()}

            sw_recordCallHistory.setOnCheckedChangeListener{_,isChecked -> editor.putBoolean("recordCallHistory",isChecked).commit()}
            sw_backupCallHistoryServer.setOnCheckedChangeListener{_,isChecked -> editor.putBoolean("backupCallHistoryServer",isChecked).commit()}

            sw_backupUserContactServer.setOnCheckedChangeListener{_,isChecked -> editor.putBoolean("backupUserContactServer",isChecked).commit()}

            sw_startUpOnBoot.setOnCheckedChangeListener{_,isChecked -> editor.putBoolean("startUpOnBoot",isChecked).commit()}
            sw_runBackground.setOnCheckedChangeListener{_,isChecked -> editor.putBoolean("runBackground",isChecked).commit()}
        }
    }

    fun setting_begin(view:View, sp:SharedPreferences){

        view.run {
            sw_gpsTrack.isChecked =  sp.getBoolean("gpsTrack",true)
            sw_gpsServer.isChecked =  sp.getBoolean("gpsServer",true)
            sw_gpsSMS.isChecked =  sp.getBoolean("gpsSMS",true)

            sw_recordCallHistory.isChecked =  sp.getBoolean("recordCallHistory",true)
            sw_backupCallHistoryServer.isChecked =  sp.getBoolean("backupCallHistoryServer",false)

            sw_backupUserContactServer.isChecked =  sp.getBoolean("backupUserContactServer",false)

            sw_startUpOnBoot.isChecked =  sp.getBoolean("startUpOnBoot",false)
            sw_runBackground.isChecked =  sp.getBoolean("runBackground",false)
        }
    }
}
