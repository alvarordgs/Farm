package com.example.farm

import android.content.ContentValues
import android.content.Context
import android.util.Log

class FarmDAO(context: Context) {
    private val myDB = DB(context)

    fun insert(farm: Farm): Boolean {
        val db = myDB.writableDatabase
        val cv = ContentValues()
        cv.put("register", farm.register)
        cv.put("name", farm.name)
        cv.put("value", farm.value)
        cv.put("latitude", farm.latitude)
        cv.put("longitude", farm.longitude)

        val res = db?.insert("farm", null, cv)
        Log.i("Teste", "Inserting.., $res")

        if (res == -1L) {
            throw Exception("Error to insert new farm!")
        }

        return true
    }

    fun list(): MutableList<Farm> {
        val list = mutableListOf<Farm>()
        val db = myDB.readableDatabase
        val sql = "SELECT * FROM farm"
        val cursor = db.rawQuery(sql, null)
        if (cursor.moveToFirst()) {
            do {
                val register = cursor.getString(cursor.getColumnIndexOrThrow("register"))
                val name = cursor.getString(cursor.getColumnIndexOrThrow("name"))
                val value = cursor.getFloat(cursor.getColumnIndexOrThrow("value"))
                val latitude = cursor.getFloat(cursor.getColumnIndexOrThrow("latitude"))
                val longitude = cursor.getFloat(cursor.getColumnIndexOrThrow("longitude"))
                val farm = Farm(register, name, value, latitude, longitude)
                list.add(farm)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun edit(farm: Farm) : Boolean{
        val db = myDB.writableDatabase
        val cv  = ContentValues()

        cv.put("name", farm.name)
        cv.put("value", farm.value)
        cv.put("latitude", farm.latitude)
        cv.put("longitude", farm.longitude)

        val affectedRows = db.update("farm", cv, "register = ?", arrayOf(farm.register))
        db.close()

        return affectedRows > 0
    }

    fun delete(register : String) : Boolean{
        val db = myDB.writableDatabase
        val affectedRows = db.delete("farm", "register = ?", arrayOf(register))
        db.close()
        return affectedRows > 0
    }

    fun backup() : String{
        val db = myDB.readableDatabase
        val data = StringBuilder()

        val farmCursor = db.rawQuery("SELECT * FROM farm", null)

        data.append("Dados fazenda: \n\n")
        if(farmCursor.moveToFirst()){
            do{
                data.append("Registro: " + farmCursor.getString(farmCursor.getColumnIndexOrThrow("register")) + "\n")
                data.append("Nome: " + farmCursor.getString(farmCursor.getColumnIndexOrThrow("name")) + "\n")
                data.append("Valor: " + farmCursor.getFloat(farmCursor.getColumnIndexOrThrow("value")) + "\n")
                data.append("Latitude: " + farmCursor.getFloat(farmCursor.getColumnIndexOrThrow("latitude")) + "\n")
                data.append("Longitude: " + farmCursor.getFloat(farmCursor.getColumnIndexOrThrow("longitude")) + "\n")
                data.append("\n")

            }while (farmCursor.moveToNext())
        }

        farmCursor.close()
        db.close()
        return data.toString()
    }
}