package com.glimpse.glimpse.manager

import android.app.Activity
import android.database.Cursor
import com.glimpse.glimpse.data.Site
import com.glimpse.glimpse.util.GlimpseTools
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class SiteManager(val activity : Activity) {

    private val glimpseTools = GlimpseTools(activity)
    private val dbHelper = glimpseTools.dbHelper()
    private val requestManager = RequestManager(glimpseTools.applicationContext())

    companion object {
        // Site related queries
        private const val GET_ALL_URLS = "SELECT url, date_added, enabled FROM glimpse_urls"
        private const val INSERT_URL = "INSERT OR IGNORE INTO glimpse_urls VALUES(?, ?, ?)"
        private const val DELETE_URL = "DELETE FROM glimpse_urls WHERE url = ?"
        private const val UPDATE_ENABLED = "UPDATE glimpse_urls SET enabled = ? WHERE url = ?"
    }

    /**
     * Read all url data from the glimpse_urls table and returns a list of Sites
     * constructed using this data.
     */
    fun sites() : List<Site> {
        val db = dbHelper.readableDatabase
        val c : Cursor = db.rawQuery(GET_ALL_URLS, emptyArray())
        val urlColIndex = c.getColumnIndex("url")
        val dateColIndex = c.getColumnIndex("date_added")
        val enabledIndex = c.getColumnIndex("enabled")

        return generateSequence { if (c.moveToNext()) c else null }
            .map {
                Site("Glimpse Site Name", c.getString(urlColIndex), c.getString(dateColIndex), c.getInt(enabledIndex) == 1)
            }
            .toList()
    }

    /**
     * Return all the beacon device names from sites which are enabled.
     */
    fun enabledDevices() : HashMap<String, Site> {
        var beacons = HashMap<String, Site>()

        sites().filter { it.enabled }.forEach{
            requestManager.getAllDeviceNamesFromSite(it, beacons)
        }

        return beacons
    }

    /**
     * Insert a url and related data into the glimpse_urls table.
     */
    fun insertURL(url : String) {
        val db = dbHelper.writableDatabase
        var date = SimpleDateFormat("MMMM DD, YYYY").format(Date())
        db.execSQL(INSERT_URL, arrayOf(url, date, 1))
    }

    /**
     * Delete all data related to the given url from the glimpse_urls table.
     */
    fun deleteURL(url : String) {
        val db = dbHelper.writableDatabase
        db.execSQL(DELETE_URL, arrayOf(url))
    }

    fun updateEnabled(url : String, enabled : Boolean) {
        val db = dbHelper.writableDatabase
        db.execSQL(UPDATE_ENABLED, arrayOf(if (enabled) 1 else 0, url))
    }
}