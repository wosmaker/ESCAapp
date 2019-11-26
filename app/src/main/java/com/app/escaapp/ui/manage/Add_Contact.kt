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
import kotlinx.coroutines.delay
import java.lang.Exception

class Add_Contact : Fragment() {

    lateinit var db : UsersDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //initial
        db = UsersDBHelper(requireContext())
        return inflater.inflate(R.layout.fragment_mange_addcontact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.btn_Add.setOnClickListener {
            addContactData(view)
            view.findNavController().popBackStack()
        }

        view.btn_Cancel.setOnClickListener {
            view.findNavController().popBackStack()
        }
    }

    private fun addContactData(view:View){
        try{
            lateinit var user : UserModel
            val id = 0
            val relate_name = view.Name_.text.toString()
            val phone_no = view.Phone_.text.toString()
            val relation = view.Relationship_.text.toString()

            val result = db.addUser(UserModel(id,relate_name,phone_no,relation,true))
            Toast.makeText(activity,"Added user :: $result",Toast.LENGTH_LONG).show()
        }catch (e : Exception){
            Toast.makeText(activity,"Error :: $e",Toast.LENGTH_LONG).show()
        }
    }
}