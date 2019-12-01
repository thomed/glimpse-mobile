package com.glimpse.glimpse.data

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.widget.Button
import com.glimpse.glimpse.ui.BeaconButton

class Beacon (
    var id : String,
    var device : BluetoothDevice,
    var context : Context
//    var listBtn : Button
) {

    var listBtn = BeaconButton(context, this)


//    init {
//        var listBtn = BeaconButton(context, this)
//    }

}
