package com.glimpse.glimpse

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class BeaconContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_beacon_content)


        this.title = intent.getStringExtra("title")
        findViewById<TextView>(R.id.beaconContentText).text = intent.getStringExtra("content")
    }
}