package com.glimpse.glimpse.data

import android.bluetooth.BluetoothDevice
import android.content.Context

class Beacon (
    var device : BluetoothDevice,
    private var context : Context
) {
    var content = ""
    var deviceName = device.name
    var friendlyName = ""
    var id = -1
}
