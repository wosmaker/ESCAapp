package com.app.escaapp.ui.emergency

import android.Manifest.permission_group.SMS
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.app.escaapp.R
import com.app.escaapp.ui.manage.ListAdapter
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import com.example.management.savehistoryModel
import kotlinx.android.synthetic.main.fragment_call_history.view.*
import kotlinx.android.synthetic.main.fragment_call_list.view.*
import kotlinx.android.synthetic.main.navbar_botton.view.*
import kotlinx.android.synthetic.main.popup_deleteall.view.*



class callListFragment : Fragment() {
    lateinit var db: UsersDBHelper
    lateinit var sp: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = UsersDBHelper(requireContext())
        return inflater.inflate(R.layout.fragment_call_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(view)
        val spName = "App_config"
        sp = requireActivity().getSharedPreferences(spName, Context.MODE_PRIVATE)

        view.run {
            dynamic_call_list.adapter =
                ListAdapter(requireActivity(), R.layout.user_customview, db.getAllUser())
            dynamic_call_list.setOnItemClickListener { adapterView, view, position, id ->
                val item = adapterView.getItemAtPosition(position) as UserModel
                callTo(item.phone_no)
            }
            nav_emergency.setOnClickListener {
                view.findNavController().popBackStack()
            }
        }
    }

    fun init(view:View){
        view.run{
            nav_profile.visibility = View.GONE
            nav_location.visibility = View.GONE
            nav_manage.visibility = View.GONE
            nav_setting.visibility = View.GONE
            nav_emergency.setBackgroundResource(R.drawable.return_home)
        }
    }

    fun callTo(phoneNumber: String){
        val intent = Intent(Intent.ACTION_CALL)
        if (isPermissionCall()){
            intent.data = Uri.parse("tel: $phoneNumber")
            Toast.makeText(activity,"calling" , Toast.LENGTH_LONG).show()
            if(sp.getBoolean("recordCallHistory",true)){
                db.saveHistory(savehistoryModel(0,phoneNumber,0.0,0.0))
            }
            if(sp.getBoolean("gpsTrack",true)){
                if(sp.getBoolean("gpsSMS",true)){
                    smsTo(phoneNumber)
                }
            }
            startActivity(intent)
        }
        else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),12)
        }
    }

    fun smsTo2(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto: $phoneNumber")
        intent.putExtra("sms_body", "Here goes your message... from wos")
        startActivity(intent)
    }

    fun smsTo(phoneNumber : String){
        val text = "Test send sms  message... from wos phone $phoneNumber"
        SmsManager.getDefault().sendTextMessage(phoneNumber,null,text,null,null)
    }

    fun isPermissionCall():Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        Toast.makeText(activity,"Permission denied" , Toast.LENGTH_LONG).show()
        return false
    }
}