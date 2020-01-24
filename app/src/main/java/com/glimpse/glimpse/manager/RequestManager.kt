package com.glimpse.glimpse.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.util.Log
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.data.Site
import org.json.JSONArray

class RequestManager(private val context : Context) {

    val requestQueue = Volley.newRequestQueue(context)

    /**
     * Gets the data for a beacon with devicename id from the specified site and returns a
     * beacon object with that data.
     */
    fun getBeacon(id : String, beacon : Beacon, site : Site) {
        var url = "${site.url}/api/beacons/$id"

        val request = JsonObjectRequest(Request.Method.GET, url, "",
            Response.Listener<JSONObject> {
                if (it["success"].toString() != "true") {
                    beacon.active = false
                } else {
                    var data = it.getJSONArray("data").getJSONObject(0)
                    Log.d("API_RESULT", data.toString())

                    beacon.content = data.get("content").toString()
                    beacon.friendlyName = data.get("display_name").toString()
                    beacon.listBtn.title.text = beacon.friendlyName
                }
            },
            Response.ErrorListener {
                Log.d("API_REQUEST", it.toString())
                beacon.active = false
            })

        requestQueue.add(request)
    }

    /**
     * Adds all the beacon device ids associated with a site to a hashset
     */
    fun getAllBeaconIdsFromSite(s : Site, beaconIds : HashMap<String, Site>) {
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

        requestQueue.add(request)
    }

}
