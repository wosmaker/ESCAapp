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
import com.app.escaapp.NavBar
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_emergency.view.*
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

        view.test1.text = "Hello Emergency"
        view.call.setOnClickListener {
            Toast.makeText(activity,"calling" , Toast.LENGTH_LONG).show()
            callTo("0888590724")
            //smsTo("0888590724")
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
