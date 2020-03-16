package com.glimpse.glimpse.manager

import android.app.Activity
import android.database.Cursor
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.util.GlimpseTools

class SavedBeaconsManager(private val activity : Activity) {

    private val glimpseTools = GlimpseTools(activity)
    private val dbHelper = glimpseTools.dbHelper()

    companion object {
        // saved beacons queries
        private const val GET_ALL_BEACONS = "SELECT * FROM saved_beacons"
    }

    fun getAllSavedBeacons() : List<Beacon> {
        val db = dbHelper.readableDatabase
        val c : Cursor = db.rawQuery(GET_ALL_BEACONS, emptyArray())
        val dNameCol = c.getColumnIndex("device_name")
        val fNameCol = c.getColumnIndex("friendly_name")
        val contentCol = c.getColumnIndex("content")
        val cTypeCol = c.getColumnIndex("content_type")

        return generateSequence { if (c.moveToNext()) c else null }
            .map {

            }.toList()
    }

}