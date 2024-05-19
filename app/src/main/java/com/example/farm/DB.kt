package com.example.farm

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DB(context: Context): SQLiteOpenHelper(context, "farm.db", null, 1) {
    companion object {
        const val TABLE_NAME = "farm"
        const val REGISTER = "register"
        const val NAME = "name"
        const val VALUE = "value"
        const val LATITUDE = "latitude"
        const val LONGITUDE = "longitude"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = "CREATE TABLE $TABLE_NAME ($REGISTER TEXT PRIMARY KEY, $NAME TEXT, $VALUE REAL, $LATITUDE REAL, $LONGITUDE REAL)"
        db.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val query = "DROP TABLE IF EXISTS $TABLE_NAME"
        db.execSQL(query)
        onCreate(db)
    }
}