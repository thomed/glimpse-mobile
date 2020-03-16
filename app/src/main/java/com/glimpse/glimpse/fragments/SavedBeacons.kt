package com.glimpse.glimpse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R
import com.glimpse.glimpse.ui.BeaconListCard
import kotlinx.android.synthetic.main.fragment_nearby_beacons.*

class SavedBeacons : Fragment() {

    private lateinit var savedBeaconsRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_beacons, container, false)
    }

    override fun onStart() {
        super.onStart()

        // if at least one saved beacon, hide text

        savedBeaconsRecyclerView = requireView().findViewById(R.id.savedBeaconListRecyclerView)
        savedBeaconsRecyclerView?.apply {
            setHasFixedSize(true)
            // set adapter
            // set layoutmanager
        }

        clearItemDecorations()
        beaconListRecyclerView.addItemDecoration(BeaconListCard.CardDivider())
    }

    private fun clearItemDecorations() {
        while (savedBeaconsRecyclerView.itemDecorationCount > 0) {
            savedBeaconsRecyclerView.removeItemDecorationAt(0)
        }
    }
}