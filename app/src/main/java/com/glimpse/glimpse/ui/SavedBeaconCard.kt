package com.glimpse.glimpse.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.util.Log
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
import com.glimpse.glimpse.data.SavedBeacon
import com.glimpse.glimpse.fragments.SavedBeacons
import com.glimpse.glimpse.manager.SavedBeaconsManager
import com.glimpse.glimpse.util.GlimpseDatabaseHelper
import com.glimpse.glimpse.util.GlimpseTools
import kotlinx.android.synthetic.main.activity_main.view.*

class SavedBeaconCard(context : Context, activity: Activity, var parentFragment : SavedBeacons) : CardView(context) {

    var cardView : CardView
    var beaconTitleTextView : TextView
    var deleteBtn : ImageButton

    var savedBeaconsManager : SavedBeaconsManager

    init {
        View.inflate(context, R.layout.ui_saved_beacon_card, this)

        savedBeaconsManager = SavedBeaconsManager(activity)

        cardView = findViewById(R.id.saved_beacon_card_view)
        beaconTitleTextView = findViewById(R.id.saved_beacon_card_title)
        deleteBtn = findViewById(R.id.deleteSavedBtn)

        animation = AnimationUtils.loadAnimation(context, R.anim.item_animation_beacon_card)
    }

    fun replace(beacon : SavedBeacon) {
        beaconTitleTextView.text = beacon.friendlyName

        cardView.setOnClickListener {
            val intent = Intent(context, BeaconContentActivity::class.java)

            intent.putExtra("title", beacon.friendlyName)
            intent.putExtra("content", beacon.content)
            intent.putExtra("content_type", beacon.contentType)
            context.startActivity(intent)

            Log.d("SAVED BEACON CARD", "Clicked")
        }

        deleteBtn.setOnClickListener {
            savedBeaconsManager.deleteBeacon(beacon.friendlyName)
            Toast.makeText(context, "Deleted Data", Toast.LENGTH_LONG).show()
            parentFragment.updateSavedBeacons()
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

