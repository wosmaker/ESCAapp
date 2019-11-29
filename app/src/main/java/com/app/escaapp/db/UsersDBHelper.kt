package com.example.management

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import java.util.*
import kotlin.collections.ArrayList

data class UserModel (val id: Int, val relate_name: String = "", val phone_no: String = "", val relation: String = "",val byUser:Boolean)

data class savehistoryModel (val id: Int, val desPhone: String = "", val latitude: Double, val longitude : Double)

data class historyModel (val id: Int, val name:String ,val desPhone: String = "",val datetime : String, val latitude: Double, val longitude : Double)



class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        const val DATABASE_NAME = "DB_userContact"
        const val DATABASE_VERSION = 1

        const val table_user = "Tb_user"
        const val id = "Userid"
        const val relate_name = "name"
        const val phone_no = "phone_no"
        const val relation = "relation"
        const val byUser = "byUser"
        const val Tb_user = "create table $table_user ($id integer primary key autoincrement not null, $relate_name text, $phone_no text, $relation text, $byUser integer not null)"

        const val table_history = "Tb_history"
        const val history_id = "history_id"
        const val des_phone = "des_phone_no"
        const val latitude = "latitude"
        const val longitude = "longitude"
        const val create_at = "datatime"
        const val Tb_history = "create table $table_history ($history_id integer primary key autoincrement not null, $des_phone text not null,$latitude real, $longitude real,$create_at default current_timestamp)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(Tb_user)
        db.execSQL(Tb_history)
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
        val rowId = writableDatabase.insert(table_user,null,values)
        return (Integer.parseInt("$rowId") != -1)
    }

    fun addAllUser(users : ArrayList<UserModel>){
        users.forEach {
            addUser(it)
            Log.d("dbaddtest",it.toString())
        }
    }

    fun deleteUser(userId : Int):Boolean{
        val sel = "$id like ?"
        val selectArgs = arrayOf(userId.toString())
        val rowId = writableDatabase.delete(table_user,sel,selectArgs)
        return (Integer.parseInt("$rowId") != -1)
    }

    fun deleteAllUser(users:ArrayList<UserModel>){
        users.forEach {
            deleteUser(it.id)
        }
    }

    fun formatUser(){
        writableDatabase.execSQL("delete from $table_user")
    }

    fun getUser(userId:Int): ArrayList<UserModel>{
        val users = ArrayList<UserModel>()
        val cursor = readableDatabase.rawQuery("select * from $table_user where $id = $userId",null)

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
        val cursor = readableDatabase.rawQuery("select * from $table_user",null)

        if(cursor!!.moveToFirst()){
            do{
                val _userId = cursor.getInt(cursor.getColumnIndex(id))
                val _name = cursor.getString(cursor.getColumnIndex(relate_name))
                val _phone = cursor.getString(cursor.getColumnIndex(phone_no))
                val _relation = cursor.getString(cursor.getColumnIndex(relation))
                val _byUser = cursor.getInt(cursor.getColumnIndex(byUser)) == 1

                users.add(UserModel(_userId,_name,_phone,_relation,_byUser))
            }while(cursor.moveToNext())
        }

        cursor.close()
        return users
    }

    fun getAllCustom():ArrayList<UserModel>{
        val customBy = ArrayList<UserModel>()

        getAllUser().forEach {
            if(it.byUser){
                customBy.add(it)
            }
        }
        return customBy
    }

    fun saveHistory(item : savehistoryModel):Boolean{
        val values = ContentValues()
        values.put(des_phone,item.desPhone)
        values.put(latitude, item.latitude)
        values.put(longitude,item.longitude)
        val rowId = writableDatabase.insert(table_history,null,values)
        return (Integer.parseInt("$rowId") != -1)
    }

    fun showAllHistory():ArrayList<historyModel>{
        val histories = ArrayList<historyModel>()
        val cursor = readableDatabase.rawQuery("select t1.* , t2.* from $table_history t1 left join $table_user t2 on (t1.$des_phone = t2.$phone_no)",null)
        if(cursor!!.moveToFirst()){
            do{
                val _id = cursor.getInt(cursor.getColumnIndex(history_id))
                val _name = cursor.getString(cursor.getColumnIndex(relate_name))
                val _phone = cursor.getString(cursor.getColumnIndex(des_phone))
                val _datetime = cursor.getString(cursor.getColumnIndex(create_at))
                val _latitude = cursor.getDouble(cursor.getColumnIndex(latitude))
                val _longitude = cursor.getDouble(cursor.getColumnIndex(longitude))


                histories.add(historyModel(_id,_name,_phone,_datetime,_latitude,_longitude))
            }while(cursor.moveToNext())
            cursor.close()
        }
        return histories
    }
}