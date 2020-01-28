package com.glimpse.glimpse.data

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.glimpse.glimpse.ui.BeaconButton

class Beacon (
    var device : BluetoothDevice,
    private var context : Context
) {
    var listBtn = BeaconButton(context, this)
    var active = true
    var content = ""
    var deviceName = device.name
    var friendlyName = ""
    var id = -1
}
