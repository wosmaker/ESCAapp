package com.app.escaapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_first_time.*
import kotlinx.android.synthetic.main.content_main.*

class FirstTime : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_time)

        bt_begin_now.setOnClickListener {
            val intent = Intent(this, MainAppActivity::class.java)
            startActivity(intent)
        }
    }

}
