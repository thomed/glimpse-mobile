package com.glimpse.glimpse.manager

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.widget.Button
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.ui.BeaconButton

class BeaconManager(val context : Context) {

    private val requestManager = RequestManager(context)
    val beacons: HashMap<String, Beacon> = HashMap()

    /**
     * Constructs a beacon and adds it to the collection
     */
    fun addBeacon(device : BluetoothDevice) {
        var id : String = device.name
        var beacon = Beacon(id, device, context)

        if (!beacons.containsKey(id)) {
            beacons[id] = beacon
        }

        requestManager.getBeacon(id, beacon)
    }

}
