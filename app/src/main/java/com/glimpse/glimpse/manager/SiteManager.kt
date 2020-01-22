package com.glimpse.glimpse.manager

import android.app.Activity
import android.database.Cursor
import com.glimpse.glimpse.data.Site
import com.glimpse.glimpse.util.GlimpseTools
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class SiteManager(val activity : Activity) {

    private val glimpseTools = GlimpseTools(activity)
    private val dbHelper = glimpseTools.dbHelper()
    private val requestManager = RequestManager(glimpseTools.applicationContext())

    companion object {
        // Site related queries
        private const val GET_ALL_URLS = "SELECT url, date_added FROM glimpse_urls"
        private const val INSERT_URL = "INSERT OR IGNORE INTO glimpse_urls VALUES(?, ?)"
        private const val DELETE_URL = "DELETE FROM glimpse_urls WHERE url = ?"
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

        return generateSequence { if (c.moveToNext()) c else null }
            .map {
                Site("Glimpse Site Name", c.getString(urlColIndex), c.getString(dateColIndex))
            }
            .toList()
    }

    /**
     * Return all the beacon device names from sites which are enabled.
     */
    fun enabledBeacons() : HashMap<String, Site> {
        var beacons = HashMap<String, Site>()

        // TODO filter to only sites which are flagged as enabled on the sites page.
        sites().forEach{
            requestManager.getAllBeaconIdsFromSite(it, beacons)
        }

        return beacons
    }

    /**
     * Insert a url and related data into the glimpse_urls table.
     */
    fun insertURL(url : String) {
        val db = dbHelper.writableDatabase
        var date = SimpleDateFormat("MMMM DD, YYYY").format(Date())
        db.execSQL(INSERT_URL, arrayOf(url, date))
    }

    /**
     * Delete all data related to the given url from the glimpse_urls table.
     */
    fun deleteURL(url : String) {
        val db = dbHelper.writableDatabase
        db.execSQL(DELETE_URL, arrayOf(url))
    }
}