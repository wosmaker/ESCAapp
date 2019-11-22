package com.app.escaapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_manage_fragment_contact.*

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
            var intent = Intent(this,MainContactActivity::class.java)

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

            startActivity(intent)
        }
    }

    fun done_input(){
        done_pop.setOnClickListener {
            var intent = Intent(this,MainContactActivity::class.java)
            add_contact()
            startActivity(intent)
        }
    }

    fun add_contact(){
        var name = relate_name.text.toString()
        var phone = relate_phone.text.toString()
        var relation = relationship.text.toString()
        var result = usersDBHelper.insertUser(UserModel(1,relate_name = name,phone_no = phone,relation = relation))
        Toast.makeText(applicationContext,"Added user : "+result,Toast.LENGTH_LONG)
    }

}
