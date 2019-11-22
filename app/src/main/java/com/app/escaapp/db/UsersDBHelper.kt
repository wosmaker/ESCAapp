package com.example.management

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        const val DATABASE_NAME = "DB_userContact"
        const val DATABASE_VERSION = 1

        const val table_name = "Tb_user"
        const val id = "Userid"
        const val relate_name = "name"
        const val phone_no = "phone_no"
        const val relation = "relation"

        const val Tb_user = "create table $table_name ($id integer primary key, $relate_name varchar, $phone_no varchar, $relation varchar"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Tb_user)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addUser(user : UserModel):Boolean{
        val db = writableDatabase
        val values = ContentValues()
        values.put(id,user.id)
        values.put(relate_name,user.relate_name)
        values.put(phone_no,user.phone_no)
        values.put(relation,user.relation)
        val rowId = db.insert(table_name,null,values)

        return (Integer.parseInt("$rowId") != -1)
    }

    fun deleteUser(userid : Int):Boolean{
        val db = writableDatabase
        val sel = "$id like ?"
        val selectArgs = arrayOf(userid.toString())
        val rowId = db.delete(table_name,sel,selectArgs)

        return (Integer.parseInt("$rowId") != -1)
    }

    fun getUser(userid:Int): ArrayList<UserModel>{
        val user = ArrayList<UserModel>()
        val db = readableDatabase
        val cursor = db.rawQuery("select * from $table_name where $id = $userid",null)

        if (cursor!!.moveToFirst()){
            do{
                var _name = cursor.getString(cursor.getColumnIndex(relate_name))
                var _phone = cursor.getString(cursor.getColumnIndex(phone_no))
                var _relation = cursor.getString(cursor.getColumnIndex(relation))

                user.add(UserModel(userid,_name,_phone,_relation))
            }while (cursor.moveToNext())
        }
        return user
    }

    fun getAllUser():ArrayList<UserModel>{
        val users = ArrayList<UserModel>()
        val db = readableDatabase
        val cursor = db.rawQuery("select * from $table_name",null)

        if(cursor!!.moveToFirst()){
            do{
                var _userId = cursor.getInt(cursor.getColumnIndex(id))
                var _name = cursor.getString(cursor.getColumnIndex(relate_name))
                var _phone = cursor.getString(cursor.getColumnIndex(phone_no))
                var _relation = cursor.getString(cursor.getColumnIndex(relation))
                users.add(UserModel(_userId,_name,_phone,_relation))
            }while(cursor.moveToNext())
        }
        return users
    }
}