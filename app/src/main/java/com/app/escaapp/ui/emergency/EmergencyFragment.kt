package com.app.escaapp.ui.emergency


import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract
import android.telephony.PhoneNumberUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.navigation.findNavController
import com.app.escaapp.NavBar
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_call_list.view.*
import kotlinx.android.synthetic.main.fragment_emergency.*
import kotlinx.android.synthetic.main.fragment_emergency.view.*
import kotlinx.android.synthetic.main.navbar_botton.view.*
import java.text.NumberFormat

/**
 * A simple [Fragment] subclass.
 */
class EmergencyFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_emergency, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        NavBar().setGo(2,view)


        view.call_police.setOnClickListener{
            callTo("0888590724")
            Toast.makeText(activity,"calling" , Toast.LENGTH_LONG).show()
        }

        view.call_hopital.setOnClickListener{
            callTo("0888590724")
            Toast.makeText(activity,"calling" , Toast.LENGTH_LONG).show()

        }

        view.call_relative.setOnClickListener{
            callTo("0888590724")
            Toast.makeText(activity,"calling" , Toast.LENGTH_LONG).show()

        }

        call_list_manage(view)






        view.btn_callHistory.setOnClickListener {
//            view.findNavController().navigate()
        }

    }

    fun call_list_manage(view: View){
        view.nav_emergency.setOnClickListener{
            view.call_list.visibility = View.VISIBLE
        }

        view.out_call_list.setOnClickListener{
            view.call_list.visibility = View.INVISIBLE
        }


    }

    fun callTo(phoneNumber: String){
        val intent = Intent(Intent.ACTION_CALL)
        if (isPermissionCall()){
            intent.data = Uri.parse("tel: $phoneNumber")
            startActivity(intent)
        }
        else {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.CALL_PHONE),12)
        }
    }

    fun smsTo(phoneNumber: String) {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("smsto: $phoneNumber")
        intent.putExtra("sms_body", "Here goes your message... from wos")
        startActivity(intent)
    }

    fun isPermissionCall():Boolean {
        if (ActivityCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED){
            return true
        }

        Toast.makeText(activity,"Permission denied" , Toast.LENGTH_LONG).show()
        return false
    }
}
