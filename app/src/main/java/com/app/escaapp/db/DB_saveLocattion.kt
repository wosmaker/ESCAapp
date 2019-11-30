package com.app.escaapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.management.UserModel
import com.example.management.UsersDBHelper


data class LocationModel(val id:Int ,val name:String, val latitude:Double, val longitude:Double, val location : String)

class DB_saveLocattion(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VESION){
    companion object {
        const val DATABASE_NAME = "location"
        const val DATABASE_VESION = 1
        const val table_name = "Tb_mark_location"
        const val id = "location_id"
        const val name = "location_name"
        const val latitude = "latitude_value"
        const val longitude = "longitude_value"
        const val location = "location_value"
        const val SQL_CREATE = "create table $table_name ($id integer primary key autoincrement not null,$name varchar,$latitude real, $longitude real,$location varchar)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addLocation( model: LocationModel):Boolean{
        val values = ContentValues()
       // values.put(id,model.id)
        values.put(name,model.name)
        values.put(latitude,model.latitude)
        values.put(longitude,model.longitude)
        values.put(location,model.location)
        val success = writableDatabase.insert(table_name,null,values)
        return (Integer.parseInt("$success") != -1)
    }

    fun deleteLocation(locationId : String):Boolean{
        val select = "$id like ?"
        val selectArgs = arrayOf(locationId)
        val success = writableDatabase.delete(table_name,select,selectArgs)
        return (Integer.parseInt("$success") != -1)
    }

    fun getLocationAll():ArrayList<LocationModel>{
        val locations = ArrayList<LocationModel>()
        val cursor = writableDatabase.rawQuery("select * from $table_name",null)

        if (cursor!!.moveToFirst()){
            do{
                val _id = cursor.getInt(cursor.getColumnIndex(id))
                val _name = cursor.getString((cursor.getColumnIndex(name)))
                val _latitude = cursor.getDouble(cursor.getColumnIndex(latitude))
                val _longitude = cursor.getDouble(cursor.getColumnIndex(longitude))
                val _location = cursor.getString((cursor.getColumnIndex(location)))
                locations.add(LocationModel(_id, _name, _latitude, _longitude,_location))
            }while(cursor.moveToNext())
        }
        return locations
    }

    /*fun updateLocation( model: LocationModel, updtnm : Boolean, updtlat : Boolean, updtlot : Boolean):Boolean{
        val values = ContentValues()
        val select = "$id = ?"
        if(updtnm) values.put(name,model.name)
        if(updtlat)values.put(latitude,model.latitude)
        if(updtlot)values.put(longitude,model.longitude)
        val selectArgs = arrayOf(model.id.toString())
        val success = writableDatabase.update(table_name,values,select,selectArgs)
        return (Integer.parseInt("$success") != -1)

    }*/

//    fun deleteTable(){
//        writableDatabase.execSQL("delete from $table_name")
//    }

    fun deleteAllLocation(location:ArrayList<LocationModel>){
        location.forEach {
            deleteLocation(it.id.toString())
        }
    }

    fun addAllLocation(location : ArrayList<LocationModel>){
        location.forEach {
            addLocation(it)
            Log.d("dbaddtest",it.toString())
        }
    }
}