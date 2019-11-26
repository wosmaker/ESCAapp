package com.example.management

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

data class UserModel (val id: Int, val relate_name: String = "", val phone_no: String = "", val relation: String = "",val byUser:Boolean)


class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        const val DATABASE_NAME = "DB_userContact"
        const val DATABASE_VERSION = 1

        const val table_name = "Tb_user"
        const val id = "Userid"
        const val relate_name = "name"
        const val phone_no = "phone_no"
        const val relation = "relation"
        const val byUser = "byUser"

        const val Tb_user = "create table $table_name ($id integer primary key autoincrement not null, $relate_name text, $phone_no text, $relation text, $byUser integer not null)"


    }

    fun initital():ArrayList<UserModel>{
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


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Tb_user)
        val callList = initital()
        addAllUesr(callList)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addUser(user : UserModel):Boolean{
        val values = ContentValues()
        values.put(relate_name,user.relate_name)
        values.put(phone_no,user.phone_no)
        values.put(relation,user.relation)
        values.put(byUser,user.byUser)
        val rowId = writableDatabase.insert(table_name,null,values)
        return (Integer.parseInt("$rowId") != -1)
    }

    fun addAllUesr(users : ArrayList<UserModel>){
        users.forEach {user ->
            addUser(user)
        }
    }

    fun deleteUser(userId : Int):Boolean{
        val sel = "$id like ?"
        val selectArgs = arrayOf(userId.toString())
        val rowId = writableDatabase.delete(table_name,sel,selectArgs)
        return (Integer.parseInt("$rowId") != -1)
    }

    fun deleteAllUser(){
        writableDatabase.execSQL("delete from $table_name")
    }

    fun getUser(userId:Int): ArrayList<UserModel>{
        val users = ArrayList<UserModel>()
        val cursor = readableDatabase.rawQuery("select * from $table_name where $id = $userId",null)

        if (cursor!!.moveToFirst()){
            do{
                var _name = cursor.getString(cursor.getColumnIndex(relate_name))
                var _phone = cursor.getString(cursor.getColumnIndex(phone_no))
                var _relation = cursor.getString(cursor.getColumnIndex(relation))
                var _byUser = cursor.getInt(cursor.getColumnIndex(byUser)) == 1

                users.add(UserModel(userId,_name,_phone,_relation,_byUser))
            }while (cursor.moveToNext())
        }
        cursor.close()
        return users
    }

    fun getAllUser():ArrayList<UserModel>{
        val users = ArrayList<UserModel>()
        val cursor = readableDatabase.rawQuery("select * from $table_name",null)

        if(cursor!!.moveToFirst()){
            do{
                var _userId = cursor.getInt(cursor.getColumnIndex(id))
                var _name = cursor.getString(cursor.getColumnIndex(relate_name))
                var _phone = cursor.getString(cursor.getColumnIndex(phone_no))
                var _relation = cursor.getString(cursor.getColumnIndex(relation))
                var _byUser = cursor.getInt(cursor.getColumnIndex(byUser)) == 1

                users.add(UserModel(_userId,_name,_phone,_relation,_byUser))
            }while(cursor.moveToNext())
        }

        cursor.close()
        return users
    }
}