package com.glimpse.glimpse.manager

import android.content.Context
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject
import android.util.Log
import com.android.volley.toolbox.Volley

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

}