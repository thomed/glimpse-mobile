package com.glimpse.glimpse.ui

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.BeaconContentActivity
import com.glimpse.glimpse.R
import com.glimpse.glimpse.data.Beacon
import com.glimpse.glimpse.data.SavedBeacon

class SavedBeaconCard(context : Context) : CardView(context) {

    var cardView : CardView
    var beaconTitleTextView : TextView

    init {
        View.inflate(context, R.layout.ui_beacon_list_card, this)

        cardView = findViewById(R.id.beacon_card_view)
        beaconTitleTextView = findViewById(R.id.beacon_card_title)

        // TODO potentially have different animation settings that user can choose
        animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_beacon_card)
    }

    fun replace(beacon : SavedBeacon) {
        beaconTitleTextView.text = beacon.friendlyName

        cardView.setOnClickListener {
            val intent = Intent(context, BeaconContentActivity::class.java)

            intent.putExtra("title", beacon.friendlyName)
            intent.putExtra("content", beacon.content)
            context.startActivity(intent)
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

