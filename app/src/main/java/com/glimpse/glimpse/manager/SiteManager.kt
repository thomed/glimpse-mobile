package com.glimpse.glimpse.manager

import android.app.Activity
import android.database.Cursor
import com.glimpse.glimpse.util.GlimpseTools

class SiteManager(val activity : Activity) {

    private val glimpseTools = GlimpseTools(activity)
    private val dbHelper = glimpseTools.dbHelper()

    companion object {
        // Site related queries
        private const val GET_ALL_URLS = "SELECT url FROM glimpse_urls"
        private const val INSERT_URL = "INSERT OR IGNORE INTO glimpse_urls VALUES(?)"
        private const val DELETE_URL = "DELETE FROM glimpse_urls WHERE url = ?"
    }

    fun urls() : List<String> {
        val db = dbHelper.readableDatabase
        val c : Cursor = db.rawQuery(GET_ALL_URLS, emptyArray())
        val urlColIndex = c.getColumnIndex("url")

        return generateSequence { if (c.moveToNext()) c else null }
            .map { c.getString(urlColIndex) }
            .toList()
    }

    fun insertURL(url : String) {
        val db = dbHelper.writableDatabase
        db.execSQL(INSERT_URL, arrayOf(url))
    }
}