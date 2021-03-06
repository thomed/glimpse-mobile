package com.glimpse.glimpse.ui

import android.content.Context
import android.graphics.Rect
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import android.widget.Switch
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R

class SiteListCard(parent : RecyclerView,context : Context) : CardView(context) {

    var cardView : CardView
    var siteNameTextView : TextView
    var siteDateAddedTextView : TextView
    var siteURLTextView : TextView
    var siteCardOptionsBtn : ImageButton
    var enableSwitch : Switch
    var menu : PopupMenu

    init {
        View.inflate(parent.context, R.layout.ui_site_list_card, this)

        cardView = findViewById(R.id.site_card_view)
        siteNameTextView = findViewById(R.id.site_name)
        siteURLTextView = findViewById(R.id.site_url)
        siteDateAddedTextView = findViewById(R.id.site_date_added)
        siteCardOptionsBtn = findViewById(R.id.siteCardOptBtn)
        enableSwitch = findViewById(R.id.siteToggleSwitch)
        menu = PopupMenu(context, siteCardOptionsBtn)
        menu.menuInflater.inflate(R.menu.site_card_options_menu, menu.menu)
    }

    fun url() : String {
        return siteURLTextView.text.toString()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        siteCardOptionsBtn.setOnClickListener {
            menu.show()
        }
    }


    // This class is used by a recyclerview to add margins around the card
    class CardDivider : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            outRect.bottom = 10
        }
    }
}