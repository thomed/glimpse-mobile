package com.glimpse.glimpse.manager

import android.app.Activity
import android.database.Cursor
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.data.SavedBeacon
import com.glimpse.glimpse.util.GlimpseTools

class SavedBeaconsManager(private val activity : Activity) {

    private val glimpseTools = GlimpseTools(activity)
    private val dbHelper = glimpseTools.dbHelper()

    companion object {
        // saved beacons queries
        private const val GET_ALL_BEACONS = "SELECT * FROM saved_beacons"
        private const val INSERT_BEACON = "INSERT OR REPLACE INTO saved_beacons VALUES(?, ?, ?, ?)"
        private const val DELETE_BEACON = "DELETE FROM saved_beacons WHERE friendly_name = ? "
    }

    fun getAllSavedBeacons() : List<SavedBeacon> {
        val db = dbHelper.readableDatabase
        val c : Cursor = db.rawQuery(GET_ALL_BEACONS, emptyArray())
        val dNameCol = c.getColumnIndex("device_name")
        val fNameCol = c.getColumnIndex("friendly_name")
        val contentCol = c.getColumnIndex("content")
        val cTypeCol = c.getColumnIndex("content_type")

        return generateSequence { if (c.moveToNext()) c else null }
            .map {
                SavedBeacon(
                    c.getString(dNameCol),
                    c.getString(fNameCol),
                    c.getString(contentCol),
                    c.getString(cTypeCol)
                )
            }.toList()
    }

    fun maketest() {
        val db = dbHelper.writableDatabase
        db.execSQL(INSERT_BEACON, arrayOf("DEVICE", "Mona Lisa", "Here is the text content", "text"))
        db.execSQL(INSERT_BEACON, arrayOf("HTMLDEVICE", "HTML Mona Lisa", "<h1>The Mona Lisa</h1><p>By Leonardo DaVinci</p>", "html"))
    }

    fun deleteBeacon(fname : String) {
        val db = dbHelper.writableDatabase
        db.execSQL(DELETE_BEACON, arrayOf(fname))
    }

    fun insertBeacon(beacon : Beacon) {
        val db = dbHelper.writableDatabase
        val dName = beacon.deviceName
        val fName = beacon.friendlyName
        val content = beacon.content
        val cType = beacon.contentType

        db.execSQL(INSERT_BEACON, arrayOf(dName, fName, content, cType))
    }

}