package com.glimpse.glimpse.data

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.glimpse.glimpse.ui.BeaconButton

class Beacon (
    var id : String,
    var device : BluetoothDevice,
    private var context : Context
) {

    var listBtn = BeaconButton(context, this)
    var active = true
    var content = ""
    var friendlyName = id

}
