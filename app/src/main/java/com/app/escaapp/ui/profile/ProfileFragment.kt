package com.app.escaapp.ui.profile

import android.content.Context

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupWindow
import android.widget.Toast
import com.app.escaapp.NavBar
import com.app.escaapp.R
import com.app.escaapp.ui.setting.SettingFragment
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.lang.Exception


class ProfileFragment : Fragment() {

    private lateinit var popupView: View
    private lateinit var popupWindow: PopupWindow

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val view = inflater!!.inflate(R.layout.fragment_profile, container, false)
        popupView = LayoutInflater.from(activity).inflate(R.layout.fragment_setting,null)
        popupWindow = PopupWindow(popupView,WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spName = "App_config"
        val sp = activity!!.getSharedPreferences(spName,Context.MODE_PRIVATE)
        NavBar().setGo(4,view)

        try{

            view.btn_popup.setOnClickListener {
                /*  val intent = Intent(activity, FirstTime::class.java)
                    startActivity(intent)*/
                popupWindow.showAsDropDown(popupView)
                SettingFragment().setting_begin(popupView,sp)
                SettingFragment().setting_listen(popupView,sp)
            }


        }
        catch (e:Exception ){
            Toast.makeText(activity,"Error  : $e"  ,10000 ).show()
        }

        /*   popupView.inside.setOnClickListener {
               popupWindow.dismiss()
           }*/
    }



}
