package com.glimpse.glimpse.manager

import android.app.Activity
import android.database.Cursor
import com.glimpse.glimpse.data.Site
import com.glimpse.glimpse.util.GlimpseTools
import java.text.SimpleDateFormat
import java.util.*

class SiteManager(val activity : Activity) {

    private val glimpseTools = GlimpseTools(activity)
    private val dbHelper = glimpseTools.dbHelper()

    companion object {
        // Site related queries
        private const val GET_ALL_URLS = "SELECT url, date_added FROM glimpse_urls"
        private const val INSERT_URL = "INSERT OR IGNORE INTO glimpse_urls VALUES(?, ?)"
        private const val DELETE_URL = "DELETE FROM glimpse_urls WHERE url = ?"
    }

    fun sites() : List<Site> {
        val db = dbHelper.readableDatabase
        val c : Cursor = db.rawQuery(GET_ALL_URLS, emptyArray())
        val urlColIndex = c.getColumnIndex("url")
        val dateColIndex = c.getColumnIndex("date_added")

        return generateSequence { if (c.moveToNext()) c else null }
            .map {
           //     c.getString(urlColIndex)
                Site("Glimpse Site Name", c.getString(urlColIndex), c.getString(dateColIndex))
            }
            .toList()
    }

    fun insertURL(url : String) {
        val db = dbHelper.writableDatabase
        var date = SimpleDateFormat("MMMM DD, YYYY").format(Date())
        db.execSQL(INSERT_URL, arrayOf(url, date))
    }
}