package com.app.escaapp.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.app.escaapp.R
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_manage.*
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.*
import java.lang.Exception

class Add_Contact : Fragment() {

    lateinit var db : UsersDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initial
        db = UsersDBHelper(this)
        return inflater.inflate(R.layout.fragment_manage_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.btn_Add.setOnClickListener {
            AddContactData(view)
        }

        view.btn_Cancel.setOnClickListener {
//            view.findNavController().navigate()
        }
    }

    fun AddContactData(view:View){
        lateinit var user : UserModel
        user.id = 0
        user.relate_name = view.Name_.text.toString()
        user.phone_no = view.Phone_.text.toString()
        user.relation = view.Relationship_.text.toString()

        try{
            val result = db.addUser(user)
            Toast.makeText(activity,"Added user :: $result",Toast.LENGTH_LONG).show()
        }catch (e : Exception){
            Toast.makeText(activity,"Error :: $e",Toast.LENGTH_LONG).show()
        }
    }
}