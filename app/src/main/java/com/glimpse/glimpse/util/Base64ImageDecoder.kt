package com.glimpse.glimpse.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.glimpse.glimpse.R
import java.lang.Exception

class Base64ImageDecoder {

    companion object {

        fun decode(b64 : String, context: Context) : Bitmap {

            val default = BitmapFactory.decodeResource(context.resources, R.mipmap.u_logo_foreground)


            // use default image if can't decode or no b64 string provided
            if (b64 == null || b64.isBlank()) {
                return default
            }

            try {
                val imageBytes = Base64.decode(b64, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.count())

                if (decodedImage == null) {
                    return default
                }

                return decodedImage
//                return default;

            } catch (e : Exception) {
                return default
            }


        }
    }

}
