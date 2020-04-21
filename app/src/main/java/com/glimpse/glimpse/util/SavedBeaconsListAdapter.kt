package com.glimpse.glimpse.util

import android.app.Activity
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.data.SavedBeacon
import com.glimpse.glimpse.fragments.SavedBeacons
import com.glimpse.glimpse.ui.SavedBeaconCard

class SavedBeaconsListAdapter(var savedBeacons : List<SavedBeacon>, var activity: Activity, var parentFragment : SavedBeacons) : RecyclerView.Adapter<SavedBeaconsListAdapter.SavedBeaconsListViewHolder>() {

    class SavedBeaconsListViewHolder(val beaconCard: SavedBeaconCard) :
        RecyclerView.ViewHolder(beaconCard)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SavedBeaconsListViewHolder {
        var card = SavedBeaconCard(parent.context, activity, parentFragment)
        return SavedBeaconsListViewHolder(card)
    }

    override fun onBindViewHolder(holder: SavedBeaconsListViewHolder, position: Int) {
        holder.beaconCard.replace(savedBeacons[position])
    }

    override fun getItemCount(): Int {
        return savedBeacons.size
    }
}