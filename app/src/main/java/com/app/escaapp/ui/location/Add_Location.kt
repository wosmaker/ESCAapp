package com.app.escaapp.ui.location

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.afollestad.vvalidator.field.FieldError
import com.afollestad.vvalidator.form
import com.app.escaapp.R
import com.app.escaapp.db.DB_saveLocattion
import com.app.escaapp.db.LocationModel
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_location_addlocation.view.*
import kotlinx.android.synthetic.main.fragment_manage.*
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.*
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.btn_Add
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.btn_Cancel
import kotlinx.coroutines.delay
import java.lang.Exception

class Add_Location : Fragment() {

    lateinit var db: DB_saveLocattion

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //initial
        db = DB_saveLocattion(requireContext())
        return inflater.inflate(R.layout.fragment_location_addlocation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        addContactData(view)


        view.btn_Cancel.setOnClickListener {
            view.findNavController().popBackStack()
        }

    }

    private fun addContactData(view: View) {
        var spName = "location"
        var sp = activity!!.getSharedPreferences(spName, Context.MODE_PRIVATE)
        form {

            input(view.location_name) {
                isNotEmpty().description("Please Input Location Name ")
                length().atLeast(2)
                length().atMost(50)
            }

            submitWith(view.btn_Cancel) {

                view.findNavController().popBackStack()
            }


            submitWith(view.btn_Add) {
                try {
                    lateinit var user: LocationModel
                    val id = 0
                    val location_name = view.location_name.text.toString()
                    val result = db.addLocation(LocationModel(id, location_name, sp.getFloat("latitude", 0.0f).toDouble(), sp.getFloat("longitude", 0.0f).toDouble(), sp.getString("location", "")!!))
                    view.findNavController().popBackStack()
                    //Toast.makeText(activity,"Added user :: $result",Toast.LENGTH_LONG).show()
                } catch (e: Exception) {
                    //Toast.makeText(activity,"Error :: $e",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}

