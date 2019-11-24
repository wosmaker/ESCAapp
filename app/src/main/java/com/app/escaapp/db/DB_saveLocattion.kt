package com.app.escaapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


data class LocationModel(val id:Int ,val name:String, val latitude:Double, val longitude:Double)

class DB_saveLocattion(context : Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,DATABASE_VESION){
    companion object {
        const val DATABASE_NAME = "location"
        const val DATABASE_VESION = 1
        const val table_name = "Tb_mark_location"
        const val id = "location_id"
        const val name = "location_name"
        const val latitude = "latitude_value"
        const val longitude = "longitude_value"

        const val SQL_CREATE = "create table $table_name ($id autoincrement primary key not null,$name varchar,$latitude real, $longitude real)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun addLocation( model: LocationModel):Boolean{
        val values = ContentValues()
        values.put(name,model.name)
        values.put(latitude,model.latitude)
        values.put(longitude,model.longitude)

        writableDatabase.insert(table_name,null,values)
    }

    fun deleteLocation(locationId : String):Boolean{
        val select = "$id like ?"
        val selectArgs = arrayOf(locationId)
        writableDatabase.delete(table_name,select,selectArgs)
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

                locations.add(LocationModel(_id, _name, _latitude, _longitude))
            }while(cursor.moveToNext())
        }
        return locations
    }

}