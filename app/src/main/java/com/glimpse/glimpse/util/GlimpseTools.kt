package com.glimpse.glimpse.util

import android.app.Activity
import android.app.Application
import android.content.Context

/**
 * A class to put common operations such as database connectivity and
 * context access.
 */
class GlimpseTools(val activity : Activity) {

    fun dbHelper() : GlimpseDatabaseHelper {
        return GlimpseDatabaseHelper(applicationContext())
    }

    fun applicationContext() : Context {
        return activity.applicationContext
    }
}