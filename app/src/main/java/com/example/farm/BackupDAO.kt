package com.example.farm

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class BackupDAO(context: Context) {

    private val myDatabase = DB(context)

    fun create(): String {
        val db = myDatabase.writableDatabase
        val data = StringBuilder()

        val cursor = db.rawQuery("SELECT * FROM farm", null)

        data.append("Farm Table: \n")
        if(cursor.moveToFirst()) {
            do {
               data.append("Register: ", cursor.getString(cursor.getColumnIndexOrThrow("register")) + "\n")
                data.append("Name: ", cursor.getString(cursor.getColumnIndexOrThrow("name")) + "\n")
                data.append("Value: ", cursor.getString(cursor.getColumnIndexOrThrow("value")) + "\n")
                data.append("Latitude: ", cursor.getString(cursor.getColumnIndexOrThrow("latitude")) + "\n")
                data.append("Longitude: ", cursor.getString(cursor.getColumnIndexOrThrow("longitude")) + "\n")
                data.append("\n")
            } while(cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return data.toString()
    }
}