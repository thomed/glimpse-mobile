package com.glimpse.glimpse.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.data.Site
import org.json.JSONArray

class RequestManager(private val context : Context) {

    val requestQueue = Volley.newRequestQueue(context)

    /**
     * Adds all the beacon device ids associated with a site to a HashMap
     * Device names are associated with a site in the HashMap
     */
    fun getAllDeviceNamesFromSite(s : Site, beaconIds : HashMap<String, Site>) {
        val url = s.url + "/api/devices"
        Log.d("API_RESULT_ALL_BEACONS", "Sending request to $url")

        val request = JsonArrayRequest(Request.Method.GET, url, "",
            Response.Listener<JSONArray> {
                Log.d("API_RESULT_ALL_BEACONS", it.toString())
                for (i in 0 until it.length()) {
                    beaconIds[it.getString(i)] = s
                }
            },
            Response.ErrorListener {
                Log.d("API_RESULT_ALL_BEACONS", "Error: $it")
            })

        request.retryPolicy = DefaultRetryPolicy(
            2000,
            2,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        requestQueue.add(request)
    }

}
