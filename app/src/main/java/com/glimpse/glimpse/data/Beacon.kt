package com.glimpse.glimpse.data

import android.bluetooth.BluetoothDevice
import android.widget.Button

data class Beacon (
    var id : String,
    var device : BluetoothDevice,
    var listBtn : Button
)
