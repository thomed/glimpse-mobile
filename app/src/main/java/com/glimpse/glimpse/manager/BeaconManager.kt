package com.glimpse.glimpse.manager

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.data.Site

class BeaconManager(val context : Context) {

    private val requestManager = RequestManager(context)
    val beacons: HashMap<String, Beacon> = HashMap()

    /**
     * Constructs a beacon and adds it to the collection
     */
    fun addBeacon(device : BluetoothDevice, site : Site) {
        var deviceName : String = device.name
        var beacon = Beacon(deviceName, device, context)

        if (!beacons.containsKey(deviceName)) {
            beacons[deviceName] = beacon
        }

        // TODO probably only get all the details when the button is selected
        // e.g. currently gets all the content, but only need name and preview pic
        // until the user wants the content
        requestManager.getBeacon(deviceName, beacon, site)
    }

}
