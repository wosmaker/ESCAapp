package com.app.escaapp.ui.manage

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
        addContactData(view)


        view.btn_Cancel.setOnClickListener {
            view.findNavController().popBackStack()
        }

    }

    private fun addContactData(view :View) {
        val relateName = arrayOf("Father", "Mother" , "" )
        form{

            input(view.Name_){
                isNotEmpty().description("Please Input Contact Name ")
                length().atLeast(2)
                length().atMost(50)
                }

            input(view.Phone_){
                isNotEmpty().description("Please Input Phone Number")
                isNumber().description("Please user Number only")
                isNumber().greaterThan(2).description("Phone number must greater than 2")
                isNumber().lessThan(11).description("Phone number must less that 11")
            }

            input(view.Relationship_){
                isNotEmpty().description("Please Input your relationship")
                length().atLeast(2)
                length().atMost(15)
            }

            submitWith(view.btn_Cancel){

                view.findNavController().popBackStack()
            }


            submitWith(view.btn_Add){
                try{
                    lateinit var user : UserModel
                    val id = 0
                    val relate_name = view.Name_.text.toString()
                    val phone_no = view.Phone_.text.toString()
                    val relation = "1212"
                    val result = db.addUser(UserModel(id,relate_name,phone_no,relation,true))
                    view.findNavController().popBackStack()
                    Toast.makeText(activity,"Added user :: $result",Toast.LENGTH_LONG).show()
                }catch (e : Exception){
                    Toast.makeText(activity,"Error :: $e",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

}

