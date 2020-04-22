package com.glimpse.glimpse.data

import android.content.Context
import com.glimpse.glimpse.util.Base64ImageDecoder

class SavedBeacon(
    var deviceName : String,
    var friendlyName : String,
    var content : String,
    var contentType : String,
    var thumbnailB64 : String,
    var context : Context
) {

    var thumbnailBitmap = Base64ImageDecoder.decode(thumbnailB64, context)

    fun updateThumbnail() {
        if (thumbnailB64.isNotBlank()) {
            thumbnailBitmap = Base64ImageDecoder.decode(thumbnailB64, context)
        }
    }
}