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

    var layout : LinearLayout
    var image : ImageView
    var viewBtn : Button
    var title : TextView

    init {
        inflate(context, R.layout.ui_beacon_button, this)
        layout = findViewById<LinearLayout>(R.id.beaconButton)
        image = layout.findViewById<ImageView>(R.id.beaconButtonImage)
        viewBtn = layout.findViewById<Button>(R.id.beaconButtonBtn)
        title = layout.findViewById<TextView>(R.id.beaconButtonTitle)
    }


    constructor(context : Context, beacon : Beacon) : super(context) {

        // TODO
        // Set the text to the friendly beacon title
        title.text = beacon.deviceName

        viewBtn.setOnClickListener {
            val intent = Intent(context, BeaconContentActivity::class.java)

            // TODO look into making beacon parcelable
            intent.putExtra("title", beacon.friendlyName)
            intent.putExtra("content", beacon.content)
            context.startActivity(intent)
        }
    }


}