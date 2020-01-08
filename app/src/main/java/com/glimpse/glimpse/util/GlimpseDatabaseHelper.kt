package com.glimpse.glimpse.util

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class GlimpseDatabaseHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME, null,
        DATABASE_VERSION
    ) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(MAKE_URL_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    companion object {
        // Database Information
        const val DATABASE_NAME = "glimpse_db"
        private val DATABASE_VERSION = 1

        // Table creation queries
        private const val MAKE_URL_TABLE =
            "CREATE TABLE IF NOT EXISTS glimpse_urls (url TEXT PRIMARY KEY, date_added TEXT)"
    }

}
