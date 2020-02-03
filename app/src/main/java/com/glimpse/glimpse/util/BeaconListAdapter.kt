package com.glimpse.glimpse.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.manager.BeaconManager
import com.glimpse.glimpse.ui.BeaconListCard
import kotlinx.android.synthetic.main.fragment_nearby_beacons.view.*

class BeaconListAdapter(private val beaconManager: BeaconManager) : RecyclerView.Adapter<BeaconListAdapter.BeaconListViewHolder>() {

    var beacons = beaconManager.beaconsList

    class BeaconListViewHolder(val beaconCard : BeaconListCard) : RecyclerView.ViewHolder(beaconCard)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BeaconListViewHolder {
        val card = BeaconListCard(parent.beaconListRecyclerView, parent.context)
        return BeaconListViewHolder(card)
    }

    override fun onBindViewHolder(holder: BeaconListViewHolder, position: Int) {
        holder.beaconCard.beaconTitleTextView.text = beacons[position].friendlyName
    }

    override fun getItemCount(): Int {
        return beacons.size
    }
}