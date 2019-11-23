package com.app.escaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
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

    lateinit var db : UsersDBHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_manage_contact)
        cancel_input()
        done_input()
        db = UsersDBHelper(this)


    }

    fun cancel_input(){
        cancel_in.setOnClickListener{

            //confirm_field_pop.visibility= View.VISIBLE
            //discard_in_pop.visibility=View.VISIBLE
            //save_in_pop.visibility=View.VISIBLE

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
            add_contact()
            var intent = Intent(this,MainContactActivity::class.java)
            startActivity(intent)
        }
    }

    fun add_contact(){

        var _name = name.text.toString()
        var _phone = phone.text.toString()
        var _relate = relate.text.toString()
        try {
            val result = db.addUser(UserModel(1,_name,_phone,_relate))
            Toast.makeText(this,"Added user : "+ result ,Toast.LENGTH_LONG).show()
        }
        catch (e: Exception){
            Toast.makeText(this,"Error  : $e"  ,Toast.LENGTH_LONG).show()
            new_Contact.text = e.toString()
        }

    }

}
