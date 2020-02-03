package com.glimpse.glimpse.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R
import kotlinx.android.synthetic.main.ui_beacon_list_card.view.*

class BeaconListCard(parent : RecyclerView, context: Context) : CardView(context) {

    var cardView : CardView
    var beaconTitleTextView : TextView

    init {
        View.inflate(parent.context, R.layout.ui_beacon_list_card, this)

        cardView = findViewById(R.id.beacon_card_view)
        beaconTitleTextView = findViewById(R.id.beacon_card_title)
    }

    class CardDivider : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            //super.getItemOffsets(outRect, view, parent, state)
            outRect.bottom = 15
        }
    }
}