package com.glimpse.glimpse.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.util.Log
import com.android.volley.toolbox.Volley
import com.glimpse.glimpse.data.Beacon

class RequestManager(private val context : Context) {

    val requestQueue = Volley.newRequestQueue(context)

    fun getJSONfromURL(url : String) {
        var espurrURL = "https://pokeapi.co/api/v2/pokemon/677"
        val request = JsonObjectRequest(Request.Method.GET, espurrURL, "",
            Response.Listener<JSONObject> {
                response -> Log.d("API_REQUEST", response["name"].toString() + " is the " + response["id"].toString() + "th pokemon.")
            },
            Response.ErrorListener {
                Log.d("API_REQUEST", it.toString())
            })

        requestQueue.add(request)
    }

    fun getBeacon(id : String, beacon : Beacon) {
        var url = "http://192.168.1.45:8080/api/beacon?id=${id}"

        val request = JsonObjectRequest(Request.Method.GET, url, "",
            Response.Listener<JSONObject> {
                if (it["success"].toString() != "true") {
                    beacon.active = false
                } else {
                    var data = it.getJSONArray("data").getJSONObject(0)
                    Log.d("API_REQUEST", data.toString())

                    // TODO JSON is swapping content and content_type
                    Log.d("API_RESULT", data.get("content_type").toString())
                    beacon.content = data.get("content_type").toString()
                }
            },
            Response.ErrorListener {
                Log.d("API_REQUEST", it.toString())
                beacon.active = false
            })

        requestQueue.add(request)
    }

}