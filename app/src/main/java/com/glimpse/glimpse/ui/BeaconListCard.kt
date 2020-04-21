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

class BeaconListCard(context : Context) : CardView(context) {

    var cardView : CardView
    var beaconTitleTextView : TextView

    init {
        View.inflate(context, R.layout.ui_beacon_list_card, this)

        cardView = findViewById(R.id.beacon_card_view)
        beaconTitleTextView = findViewById(R.id.beacon_card_title)

        // TODO potentially have different animation settings that user can choose
        animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_beacon_card)
    }

    fun replace(beacon : Beacon) {
        beaconTitleTextView.text = beacon.friendlyName

        cardView.setOnClickListener {
            val intent = Intent(context, BeaconContentActivity::class.java)

            // TODO look into making beacon parcelable so can directly pass it and its content
            // or do all of the content-getting in the new activity
            intent.putExtra("title", beacon.friendlyName)
            intent.putExtra("content", beacon.content)
            intent.putExtra("content_type", beacon.contentType)
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
