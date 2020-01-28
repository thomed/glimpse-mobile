package com.glimpse.glimpse.manager

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.data.Site
import org.json.JSONObject

class BeaconManager(private val parent : Activity) {

    private val siteManager : SiteManager = SiteManager(parent)
    val enabledDevices : HashMap<String, Site> = siteManager.enabledDevices()
    val beacons: HashMap<String, ArrayList<Beacon>> = HashMap()

    /**
     * Constructs a beacon and adds it to the collection
     */
//    fun addBeacon(device : BluetoothDevice) {
//        var deviceName : String = device.name
//        var beacon = Beacon(deviceName, device, parent)
//
//        if (!beacons.containsKey(deviceName)) {
//            beacons[deviceName] = beacon
//        }
//
//        // TODO probably only get all the details when the button is selected
//        // e.g. currently gets all the content, but only need name and preview pic
//        // until the user wants the content
////        requestManager.getBeacon(deviceName, beacon, site)
//    }

    /**
     * Given a device:
     *  - If the device is found to an enabled device, queries the associated site for basic beacon data
     *  - Upon success, calls the callback function so the caller knows new beacon information is present.
     *
     *  TODO
     *  Eventually the API needs to just provide friendly name, id, and preview image leaving the
     *  content for when the user wants to view it.
     */
    fun getBeaconsByDevice(device : BluetoothDevice, callback : () -> Unit) {
        if (!enabledDevices.containsKey(device.name)) {
            return
        }

        var url = "${enabledDevices[device.name]}/api/beacons?device_name=${device.name}"
        val request = JsonObjectRequest(Request.Method.GET, url, "",
            Response.Listener<JSONObject> {
                if (it["success"].toString() == "true") {

//                    if (!beacons.containsKey(device.name)) {
                    beacons[device.name] = ArrayList()
//                    }

                    var data = it.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        var beaconObj = data.getJSONObject(i)
                        var newBeacon = Beacon(device, parent)
                        newBeacon.id = beaconObj.getInt("id")
                        newBeacon.friendlyName = beaconObj.getString("display_name")
                        newBeacon.listBtn.title.text = newBeacon.friendlyName

                        // TODO should eventually not request content until user makes selection on NearbyDevices
                        newBeacon.content = beaconObj.getString("content")
                        beacons[device.name]?.add(newBeacon)
                    }

                    callback()
                }
            },
            Response.ErrorListener {
                Log.d("BEACON_MANAGER", "Error: $it")
            })
    }

}
