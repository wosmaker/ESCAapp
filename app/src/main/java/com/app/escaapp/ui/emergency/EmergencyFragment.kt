package com.app.escaapp.ui.emergency


import android.app.AlertDialog
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
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.escaapp.NavBar
import com.app.escaapp.R
import com.app.escaapp.ui.manage.ListAdapter
import com.app.escaapp.ui.manage.UserAdapter
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import com.example.management.savehistoryModel
import kotlinx.android.synthetic.main.fragment_emergency.view.*
import kotlinx.android.synthetic.main.navbar_botton.view.*
import kotlinx.android.synthetic.main.select_relate.view.*
import kotlinx.android.synthetic.main.user_customview.view.*
import java.util.*
import kotlin.concurrent.schedule

/**
 * A simple [Fragment] subclass.
 */
class EmergencyFragment : Fragment() {

    lateinit var db: UsersDBHelper
    lateinit var sp: SharedPreferences
    lateinit var edit : SharedPreferences.Editor
    lateinit var sp_location : SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = UsersDBHelper(requireContext())
        val spName = "App_config"
        sp = requireActivity().getSharedPreferences(spName, Context.MODE_PRIVATE)
        edit = sp.edit()
        val spLocation = "location"
        sp_location = requireActivity().getSharedPreferences(spLocation, Context.MODE_PRIVATE)

        return inflater.inflate(R.layout.fragment_emergency, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        NavBar().setGo(2,view)


        if (sp.getBoolean("FirstRun", true)) {
            view.findNavController().navigate(R.id.emergency_firstrun)
        }


        view.run{

            location.run {
                text = sp_location.getString("location", "Not found")
                invalidate()
            }

            btn_police.setOnClickListener {
                callTo("911")
            }

            btn_hospital.setOnClickListener{
                callTo("1669")
            }

            btn_relative.setOnClickListener{
                val relate_call:String = sp.getString("relate_phone","0888590724")!!
                callTo(relate_call)
            }

            btn_relative.setOnLongClickListener {
                val selectview = LayoutInflater.from(requireContext()).inflate(R.layout.select_relate,null)
                val dialog = AlertDialog.Builder(requireContext())
                    .setView(selectview)
                    .create()

                val customContact = db.getAllCustom()
                if (customContact.size > 0){
                    dialog.show()
                    selectview.run{
                        SelectRelateView.adapter = ListAdapter(requireActivity(), R.layout.user_customview, customContact)
                        SelectRelateView.run{
                            setOnItemClickListener { adapterView, view, position, id ->
                                val item = adapterView.getItemAtPosition(position) as UserModel
                                edit.putString("relate_phone",item.phone_no).commit()
                                dialog.dismiss()
                            }
                        }
                    }
                }
                else{
                    edit.putString("relate_phone","0888590724").commit()
                    Toast.makeText(activity,"No custom relate for setup",Toast.LENGTH_LONG).show()
                }
                true
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
        val latitude = sp_location.getFloat("latitude",0.0f)
        val longitude = sp_location.getFloat("longitude",0.0f)
        val place = sp_location.getString("location","dont't know")
        val text = "{ " +
                "Emgergency : 'test app' ,\n" +
                "latitude   : $latitude ,\n" +
                "longitude  : $longitude ,\n" +
                "location   : $place\n ," +
                "}"

        intent.putExtra("sms_body", text)
        startActivity(intent)
    }

    fun smsTo(phoneNumber : String){
        val latitude = sp_location.getFloat("latitude",0.0f)
        val longitude = sp_location.getFloat("longitude",0.0f)
        val place = sp_location.getString("location","dont't know")
        val text = "Emgergency : 'test app' ,\n" +
                "latitude   : $latitude ,\n" +
                "longitude  : $longitude"

        if (isPermissionSms()){
            SmsManager.getDefault().sendTextMessage(phoneNumber,null,text,null,null)
        }
    }
    fun isPermissionCall():Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        Toast.makeText(activity,"Permission denied" , Toast.LENGTH_LONG).show()
        return false
    }

    fun isPermissionSms():Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
            return true
        }
        Toast.makeText(activity,"Permission denied" , Toast.LENGTH_LONG).show()
        return false
    }
}
