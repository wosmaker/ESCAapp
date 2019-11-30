package com.app.escaapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.management.UserModel
import com.example.management.UsersDBHelper

import kotlinx.android.synthetic.main.activity_first_time.*
import java.util.*
import kotlin.concurrent.schedule

class FirstTime: Fragment(){

    private lateinit var db:UsersDBHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = UsersDBHelper(requireContext())
        return inflater.inflate(R.layout.activity_first_time, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val spName = "App_config"
        val sp = activity!!.getSharedPreferences(spName, Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sp.edit()

        if (sp.getBoolean("FirstRun", true)) {
            editor.putBoolean("FirstRun", false).commit()
            db.addAllUser(inititalContact())
        }

        view.run {
            bt_begin_now.setOnClickListener {
                view.findNavController().navigate(R.id.firstrun_emergency)
            }

            bt_login.setOnClickListener {
                val dialog = popup().notInclude(requireContext())
                Timer().schedule(1200) {
                    dialog.dismiss()
                }
            }
        }
    }

    private fun inititalContact(): ArrayList<UserModel> {
        val defaultCall = ArrayList<UserModel>()
        defaultCall.add(UserModel(0, "เหตุด่วน เหตุร้าย (เบอร์ฉุกเฉินแห่งชาติ)", "911", "", false))
        defaultCall.add(UserModel(0, "ศูนย์บริการข่าวสารข้อมูลและรับเรื่องร้องเรียน", "1599", "", false))
        defaultCall.add(UserModel(0, "ตำรวจท่องเที่ยว", "1155", "", false))
        defaultCall.add(UserModel(0, "ตำรวจทางหลวง", "1193", "", false))
        defaultCall.add(UserModel(0, "ตำรวจกองปราบ", "1195", "", false))
        defaultCall.add(UserModel(0, "ตำรวจจราจร ศูนย์ควบคุมและสั่งการจราจร", "1197", "", false))
        defaultCall.add(UserModel(0, "โรงพยาบาลตำรวจแ", "1691", "", false))
        defaultCall.add(UserModel(0, "ตำรวจตระเวนชายแดน", "1190", "", false))
        defaultCall.add(UserModel(0, "ตำรวจตรวจคนเข้าเมือง", "1178", "", false))
        defaultCall.add(UserModel(0, "ตำรวจน้ำ อุบัติเหตุทางน้ำ", "1196", "", false))
        defaultCall.add(UserModel(0, "ตำรวจรถไฟ", "1690", "", false))

        defaultCall.add(UserModel(0, "ศูนย์เตือนภัยพิบัติแห่งชาติ", "192", "", false))
        defaultCall.add(UserModel(0, "แจ้งอัคคีภัย สัตว์เข้าบ้าน สำนักป้องกันและบรรเทาสาธารณภัย", "1690", "", false))
        defaultCall.add(UserModel(0, "หน่วยแพทย์กู้ชีวิต", "1554", "", false))
        defaultCall.add(UserModel(0, "การท่องเที่ยวแห่งประเทศไทย", "1672", "", false))
        defaultCall.add(UserModel(0, "สถาบันการแพทย์ฉุกเฉินแห่งชาติ", "1669", "", false))
        defaultCall.add(UserModel(0, "ศูนย์เตือนภัยพิบัติแห่งชาติ", "1860", "", false))
        return defaultCall
    }
}