package com.app.escaapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_manage.*

class MainContactActivity : AppCompatActivity() {
    lateinit var db: UsersDBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_manage)
       // db=UsersDBHelper(this)

//        edit_view_animation()
//        add_contact()
//        cancel_edit()
//        done_edit()
    }
//
//    fun edit_view_animation() {
//
//        edit_but.setOnClickListener {
//            val fade = AnimationUtils.loadAnimation(this, R.anim.fade)
//            edit_but.startAnimation(fade)
//            val sd = AnimationUtils.loadAnimation(this,R.anim.slide_down)
//            block1.startAnimation(sd)
//            val sd_add = AnimationUtils.loadAnimation(this,R.anim.fade_in)
//
//            edit_but.visibility = View.GONE
//            block1.translationY = 80F
//
//            add.startAnimation(sd_add)
//            Cancel.startAnimation(sd_add)
//            Done.startAnimation(sd_add)
//
//            Cancel.visibility=View.VISIBLE
//            Done.visibility=View.VISIBLE
//            add.visibility=View.VISIBLE
//        }
//    }
//
//    private fun add_contact()
//    {
//        add.setOnClickListener {
//            val intent = Intent(this,contact::class.java)
//            startActivity(intent)
//        }
//    }
//
//    private fun cancel_edit()
//    {
//
//        Cancel.setOnClickListener {
//            val fade = AnimationUtils.loadAnimation(this, R.anim.fade_in)
//            edit_but.startAnimation(fade)
//            val sd = AnimationUtils.loadAnimation(this, R.anim.slide_up)
//            block1.startAnimation(sd)
//            val sd_add = AnimationUtils.loadAnimation(this, R.anim.fade)
//
//            edit_but.visibility = View.VISIBLE
//            block1.translationY = 0F
//            add.startAnimation(sd_add)
//            Cancel.startAnimation(sd_add)
//            Done.startAnimation(sd_add)
//
//            Cancel.visibility = View.INVISIBLE
//            Done.visibility = View.INVISIBLE
//            add.visibility = View.INVISIBLE
//        }
//    }
//
//    private fun done_edit()
//    {
//        Done.setOnClickListener {
//            val fade = AnimationUtils.loadAnimation(this, R.anim.fade_in)
//            edit_but.startAnimation(fade)
//            val sd = AnimationUtils.loadAnimation(this, R.anim.slide_up)
//            block1.startAnimation(sd)
//            val sd_add = AnimationUtils.loadAnimation(this, R.anim.fade)
//            edit_but.visibility = View.VISIBLE
//            block1.translationY = 0F
//            add.startAnimation(sd_add)
//            Cancel.startAnimation(sd_add)
//            Done.startAnimation(sd_add)
//            Cancel.visibility = View.INVISIBLE
//            Done.visibility = View.INVISIBLE
//            add.visibility = View.INVISIBLE
//            var result=db.getAllUser()
//            Toast.makeText(this,"user : "+ result , Toast.LENGTH_LONG).show()
//        }
//    }
}
