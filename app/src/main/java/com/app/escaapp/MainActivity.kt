package com.app.escaapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val spName = "App_config"
        val sp:SharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sp.edit()

        if (sp.getBoolean("FirstRun",true)){
            editor.putBoolean("FirstRun",false)
            editor.commit()

            val intent = Intent(this, FirstTime::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, MainAppActivity::class.java)
            startActivity(intent)
        }

    }


}
