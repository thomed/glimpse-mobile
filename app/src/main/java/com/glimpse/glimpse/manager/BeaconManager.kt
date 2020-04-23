package com.glimpse.glimpse.manager

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.data.Site
import com.glimpse.glimpse.util.BeaconListDiffUtil
import com.glimpse.glimpse.util.GlimpseTools
import org.json.JSONObject

class BeaconManager(private val parent : Activity) {

    private val savedBeaconManager = SavedBeaconsManager(parent)
    private val requestQueue = Volley.newRequestQueue(parent)
    private val siteManager : SiteManager = SiteManager(parent)
    val enabledDevices : HashMap<String, Site> = siteManager.enabledDevices()
    val beacons : HashMap<String, ArrayList<Beacon>> = HashMap()
    val beaconsList = ArrayList<Beacon>()
    private val previousBeaconsList = ArrayList<Beacon>()

    /**
     * When a discovery cycle ends, compare the current list to the old one and apply the diff.
     */
    fun newDiscoveryCycleDiff(callback : (diff : DiffUtil.DiffResult) -> Unit) {
        // if first discovery cycle just finished
        if (previousBeaconsList.isEmpty()) {
            previousBeaconsList.addAll(beaconsList)
        }

        // else find the diff between previous and current, update previous to current
        updateBeaconList()
        var diff = DiffUtil.calculateDiff(BeaconListDiffUtil(previousBeaconsList, beaconsList))
        previousBeaconsList.clear()
        previousBeaconsList.addAll(beaconsList)
        beacons.clear()
        callback(diff)
    }

    /**
     *
     * Refresh the beacon list so that it is up to date with all of the beacons in the beacons
     * hashmap.
     *
     * This should be called whenever the beacons hashmap is updated to keep things relevant for the
     * recycler view of the nearby beacons page.
     *
     * There may be a more efficient way to maintain both the deviceName -> beacons relation
     * while working with a recycler view, but none comes to mind right now.
     */
    fun updateBeaconList() : DiffUtil.DiffResult {
        var newList = ArrayList<Beacon>()
        for (deviceName in beacons) {
            deviceName.value.forEach {
                newList.add(it)
            }
        }

        var diff = DiffUtil.calculateDiff(BeaconListDiffUtil(beaconsList, newList))

        beaconsList.clear()
        beaconsList.addAll(newList)
        return diff
    }

    /**
     * Given a device:
     *  - If the device is found to be an enabled device, queries the associated site for basic beacon data.
     *  - Upon success, calls the callback function so the caller knows new beacon information is present.
     *
     *  TODO
     *  Eventually the API needs to just provide friendly name, id, and preview image leaving the
     *  content for when the user wants to view it.
     */
    fun getBeaconsByDevice(device : BluetoothDevice, rssi : Short, callback : (diff : DiffUtil.DiffResult) -> Unit) {
        if (!enabledDevices.containsKey(device.name)) {
            return
        }

        var url = "${enabledDevices[device.name]?.url}/api/beacons?device_name=${device.name}"
        val request = JsonObjectRequest(Request.Method.GET, url, "",
            Response.Listener<JSONObject> {
                if (it["success"].toString() == "true") {

                    beacons[device.name] = ArrayList()

                    var data = it.getJSONArray("data")
                    for (i in 0 until data.length()) {
                        var beaconObj = data.getJSONObject(i)
                        var newBeacon = Beacon(device, parent)

                        // only add if rssi is within threshold
//                        if (beaconObj.getInt("minRSSI") > rssi) {
//                            continue
//                        }

                        newBeacon.id = beaconObj.getInt("id")
                        newBeacon.friendlyName = beaconObj.getString("display_name")
                        newBeacon.rssi = rssi
                        newBeacon.thumbnailB64 = beaconObj.getString("thumbnail")
                        newBeacon.thumbnailB64 = newBeacon.thumbnailB64.substringAfter("base64,")
                        newBeacon.updateThumbnail()


                        newBeacon.content = beaconObj.getString("content")
                        newBeacon.contentType = beaconObj.getString("content_type")
                        beacons[device.name]?.add(newBeacon)
                    }

                    callback(updateBeaconList())
                }
            },
            Response.ErrorListener {
                Log.d("BEACON_MANAGER", "Error: $it")
            })

        request.retryPolicy = DefaultRetryPolicy(
            2000,
            2,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)

        requestQueue.add(request)
    }

    fun saveBeacon(beacon : Beacon) {
        savedBeaconManager.insertBeacon(beacon)
    }

}
