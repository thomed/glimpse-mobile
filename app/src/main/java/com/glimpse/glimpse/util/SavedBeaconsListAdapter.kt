package com.glimpse.glimpse.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.ui.SavedBeaconCard

class SavedBeaconsListAdapter() : RecyclerView.Adapter<SavedBeaconsListAdapter.SavedBeaconsListViewHolder>() {

    class SavedBeaconsListViewHolder(val beaconCard: SavedBeaconCard) :
        RecyclerView.ViewHolder(beaconCard)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedBeaconsListViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(holder: SavedBeaconsListViewHolder, position: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}