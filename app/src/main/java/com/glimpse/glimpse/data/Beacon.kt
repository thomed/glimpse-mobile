package com.glimpse.glimpse.data

import android.bluetooth.BluetoothDevice
import android.content.Context
import com.glimpse.glimpse.util.Base64ImageDecoder

class Beacon (
    var device : BluetoothDevice,
    private var context : Context
) {
    var content = ""
    var contentType = ""
    var deviceName = device.name
    var friendlyName = ""
    var id = -1
    var rssi : Short = -100
    var thumbnailB64 = ""
    var thumbnailBitmap = Base64ImageDecoder.decode("", context)

    fun updateThumbnail() {
        if (thumbnailB64.isNotBlank()) {
            thumbnailBitmap = Base64ImageDecoder.decode(thumbnailB64, context)
        }
    }
}
