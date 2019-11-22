package com.app.escaapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.util.*


class DB_callList(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "DB_call_list"

        const val table_name = "Tb_callList"
        const val id = "callId"
        const val name = "Name"
        const val phone = "phone_Number"

        private val Tb_callList =
            "CREATE TABLE $table_name ($id INTEGER PRIMARY KEY, $name VARCHAR , $phone INTEGER )"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        //create table
        db?.execSQL(Tb_callList)
    }

    fun addContact():Boolean{
        val db = writableDatabase
        val values = ContentValues()

        val _success = db.insert(table_name,null,values)
        Log.v("InsertedId","$_success")
        return (Integer.parseInt("$_success") != -1)
    }

    fun getCallAll():String{
        var all = ""
        val db = readableDatabase
        val selectAll = "SELECT * FROM $table_name"
        val cursor = db.rawQuery(selectAll,null)
        if (cursor != null && cursor.moveToFirst()){
            do {
                var name = cursor.getString(cursor.getColumnIndex(name))
                var phone = cursor.getString(cursor.getColumnIndex(phone))
                all = "$all\n$name -> $phone "
            } while(cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return all
    }

}