package com.glimpse.glimpse.data

import android.bluetooth.BluetoothDevice
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.glimpse.glimpse.ui.BeaconButton

class Beacon (
    var id : String,
    var device : BluetoothDevice,
    var context : Context
//    var listBtn : Button
) {

    var listBtn = BeaconButton(context, this)
    var active = true
    var content = ""

//    init {
//        listBtn.setOnClickListener {
//            Toast.makeText(context, "CLICKED", Toast.LENGTH_LONG)
//            Log.d("BEACON_BUTTON", "Clicked a button")
//        }
//    }

}
