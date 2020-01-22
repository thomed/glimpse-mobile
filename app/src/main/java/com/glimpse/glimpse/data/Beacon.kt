package com.glimpse.glimpse.data

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.glimpse.glimpse.ui.BeaconButton

class Beacon (
    var deviceName : String,
    var device : BluetoothDevice,
    private var context : Context
) {

    var listBtn = BeaconButton(context, this)
    var active = true
    var content = ""
    var friendlyName = ""

}
