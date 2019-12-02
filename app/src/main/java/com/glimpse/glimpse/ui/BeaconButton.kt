package com.glimpse.glimpse.ui

import android.content.Context
import android.content.Intent
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.glimpse.glimpse.BeaconContentActivity
import com.glimpse.glimpse.R
import com.glimpse.glimpse.data.Beacon

class BeaconButton : LinearLayout {

    init {
        inflate(context, R.layout.beacon_button, this)
    }


    constructor(context : Context, beacon : Beacon) : super(context) {
        var layout = findViewById<LinearLayout>(R.id.beaconButton)
        var image = layout.findViewById<ImageView>(R.id.beaconButtonImage)
        var viewBtn = layout.findViewById<Button>(R.id.beaconButtonBtn)
        var title = layout.findViewById<TextView>(R.id.beaconButtonTitle)
//
//        // TODO
//        // Set the text to the friendly beacon title
        title.text = beacon.id

        viewBtn.setOnClickListener {
            val intent = Intent(context, BeaconContentActivity::class.java)

            // TODO look into making beacon parcelable
            intent.putExtra("title", beacon.id)
            intent.putExtra("content", beacon.content)
            context.startActivity(intent)
        }
    }


}