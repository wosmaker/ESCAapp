package com.app.escaapp.ui.emergency


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.app.escaapp.NavBar
import com.app.escaapp.R
import com.example.management.UsersDBHelper
import com.example.management.savehistoryModel
import kotlinx.android.synthetic.main.fragment_emergency.view.*
import kotlinx.android.synthetic.main.navbar_botton.view.*

/**
 * A simple [Fragment] subclass.
 */
class EmergencyFragment : Fragment() {

    lateinit var db: UsersDBHelper
    lateinit var sp: SharedPreferences
    lateinit var sp2: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = UsersDBHelper(requireContext())
        return inflater.inflate(R.layout.fragment_emergency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        NavBar().setGo(2,view)
        val spName = "App_config"
        sp = requireActivity().getSharedPreferences(spName, Context.MODE_PRIVATE)

        if (sp.getBoolean("FirstRun", true)) {
            view.findNavController().navigate(R.id.emergency_firstrun)
        }

        val spName2 = "location"
        sp2 = requireActivity().getSharedPreferences(spName2, Context.MODE_PRIVATE)

        view.run{
            btn_police.setOnClickListener {
                callTo("0888590724")
            }

            btn_hospital.setOnClickListener{
                callTo("0888590724")
            }

            btn_relative.setOnClickListener{
                callTo("0888590724")
            }

            btn_callHistory.setOnClickListener{
                view.findNavController().navigate(R.id.emergency_history)
            }

            nav_emergency.setOnClickListener{
                view.findNavController().navigate(R.id.emergency_callList)
            }
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
