package com.app.escaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_manage_contact.*
import kotlinx.android.synthetic.main.fragment_manage_fragment_contact.*
import kotlinx.android.synthetic.main.fragment_manage_fragment_contact.cancel_in
import kotlinx.android.synthetic.main.fragment_manage_fragment_contact.done_pop
import java.lang.Exception

class contact : AppCompatActivity() {

    lateinit var usersDBHelper : UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_manage_contact)
        cancel_input()
        done_input()


    }

    fun cancel_input(){
        cancel_in.setOnClickListener{

            //          //confirm_field.visibility=View.VISIBLE
            //            // discard_in.visibility=View.VISIBLE
            //            //save_in.visibility=View.VISIBLE

            /*discard_in.setOnClickListener {
                startActivity(intent_in)
            }
            save_in.setOnClickListener {
               // confirm_field.visibility=View.INVISIBLE
                discard_in.visibility=View.INVISIBLE
                save_in.visibility=View.INVISIBLE
            }*/

            var intent = Intent(this,MainContactActivity::class.java)
            startActivity(intent)
        }
    }

    fun done_input(){
        done_pop.setOnClickListener {
            //example


            var intent = Intent(this,MainContactActivity::class.java)
            add_contact()
            //startActivity(intent)
        }
    }

    fun add_contact(){
        var _name = name.text.toString()
        var _phone = phone.text.toString()
        var _relate = relate.text.toString()
        try {
            var result = usersDBHelper.insertUser(UserModel(1,relate_name = _name,phone_no = _phone,relation = _relate))
            Toast.makeText(applicationContext,"Added user : "+ result ,Toast.LENGTH_LONG)
            new_Contact.text = result.toString()
        }
        catch (e: Exception){
            Toast.makeText(applicationContext,"Error  : $e"  ,Toast.LENGTH_LONG)
            new_Contact.text = e.toString()
        }

    }

}
