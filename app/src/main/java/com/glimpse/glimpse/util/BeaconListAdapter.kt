package com.glimpse.glimpse.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.fragments.NearbyBeacons
import com.glimpse.glimpse.manager.BeaconManager
import com.glimpse.glimpse.ui.BeaconListCard

class BeaconListAdapter(beaconManager: BeaconManager, var parentFragment : NearbyBeacons) : RecyclerView.Adapter<BeaconListAdapter.BeaconListViewHolder>() {

    var beacons = beaconManager.beaconsList

    class BeaconListViewHolder(val beaconCard : BeaconListCard) : RecyclerView.ViewHolder(beaconCard)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : BeaconListViewHolder {
        val card = BeaconListCard(parent.context, parentFragment)
        return BeaconListViewHolder(card)
    }

    override fun onBindViewHolder(holder: BeaconListViewHolder, position: Int) {
        holder.beaconCard.replace(beacons[position])
        holder.beaconCard.animate()
    }

    override fun getItemCount() : Int {
        return beacons.size
    }
}