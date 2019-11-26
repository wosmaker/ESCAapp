package com.app.escaapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    val LocationPermissionRequestCode = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val spName = "App_config"
        val sp:SharedPreferences = getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sp.edit()


        if (sp.getBoolean("FirstRun",true)){
            editor.putBoolean("FirstRun",false)
            editor.commit()

            try {
                val users = initital()
                val db = UsersDBHelper(this)
                db.addAllUesr(users)
            }catch (e:Exception){  Toast.makeText(this,"Error $e", Toast.LENGTH_LONG).show() }

            val intent = Intent(this, FirstTime::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, MainAppActivity::class.java)
            startActivity(intent)
        }
    }

    fun initital(): ArrayList<UserModel> {
        val defaultCall = ArrayList<UserModel>()
        defaultCall.add(UserModel(0,"เหตุด่วน เหตุร้าย (เบอร์ฉุกเฉินแห่งชาติ)","911","",false))
        defaultCall.add(UserModel(0,"ศูนย์บริการข่าวสารข้อมูลและรับเรื่องร้องเรียน","1599","",false))
        defaultCall.add(UserModel(0,"ตำรวจท่องเที่ยว","1155","",false))
        defaultCall.add(UserModel(0,"ตำรวจทางหลวง","1193","",false))
        defaultCall.add(UserModel(0,"ตำรวจกองปราบ","1195","",false))
        defaultCall.add(UserModel(0,"ตำรวจจราจร ศูนย์ควบคุมและสั่งการจราจร","1197","",false))
        defaultCall.add(UserModel(0,"โรงพยาบาลตำรวจแ","1691","",false))
        defaultCall.add(UserModel(0,"ตำรวจตระเวนชายแดน","1190","",false))
        defaultCall.add(UserModel(0,"ตำรวจตรวจคนเข้าเมือง","1178","",false))
        defaultCall.add(UserModel(0,"ตำรวจน้ำ อุบัติเหตุทางน้ำ","1196","",false))
        defaultCall.add(UserModel(0,"ตำรวจรถไฟ","1690","",false))

        defaultCall.add(UserModel(0,"ศูนย์เตือนภัยพิบัติแห่งชาติ","192","",false))
        defaultCall.add(UserModel(0,"แจ้งอัคคีภัย สัตว์เข้าบ้าน สำนักป้องกันและบรรเทาสาธารณภัย","1690","",false))
        defaultCall.add(UserModel(0,"หน่วยแพทย์กู้ชีวิต","1554","",false))
        defaultCall.add(UserModel(0,"การท่องเที่ยวแห่งประเทศไทย","1672","",false))
        defaultCall.add(UserModel(0,"สถาบันการแพทย์ฉุกเฉินแห่งชาติ","1669","",false))
        defaultCall.add(UserModel(0,"ศูนย์เตือนภัยพิบัติแห่งชาติ","1860","",false))

        return defaultCall
    }
}
