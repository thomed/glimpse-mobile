package com.glimpse.glimpse.ui

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.BeaconContentActivity
import com.glimpse.glimpse.R
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.fragments.NearbyBeacons

class BeaconListCard(context : Context, var parentFragment : NearbyBeacons) : CardView(context) {

    var cardView : CardView
    var beaconTitleTextView : TextView
    var saveBeaconBtn : ImageButton

    init {
        View.inflate(context, R.layout.ui_beacon_list_card, this)

        cardView = findViewById(R.id.beacon_card_view)
        beaconTitleTextView = findViewById(R.id.beacon_card_title)
        saveBeaconBtn = findViewById(R.id.saveBeaconBtn)

        animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_beacon_card)
    }

    fun replace(beacon : Beacon) {
        beaconTitleTextView.text = beacon.friendlyName

        cardView.setOnClickListener {
            val intent = Intent(context, BeaconContentActivity::class.java)

            intent.putExtra("title", beacon.friendlyName)
            intent.putExtra("content", beacon.content)
            intent.putExtra("content_type", beacon.contentType)
            context.startActivity(intent)
        }

        saveBeaconBtn.setOnClickListener {
            parentFragment.saveBeacon(beacon)
            Toast.makeText(context, "Saved Data", Toast.LENGTH_LONG).show()
        }
    }

    class CardDivider : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = 15
        }
    }
}
