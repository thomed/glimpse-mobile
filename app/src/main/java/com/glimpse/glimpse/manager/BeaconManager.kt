package com.glimpse.glimpse.manager

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.widget.Button
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.ui.BeaconButton

class BeaconManager(_context : Context) {
    private val context = _context
    val beacons: HashMap<String, Beacon> = HashMap()

    /**
     * Constructs a beacon and adds it to the collection
     */
    fun addBeacon(device : BluetoothDevice) {
        var id : String = device.name
        if (!beacons.containsKey(id)) {
            beacons[id] = Beacon(id, device, context)
        }
    }

}
