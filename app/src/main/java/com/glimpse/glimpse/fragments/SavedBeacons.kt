package com.glimpse.glimpse.fragments

import android.os.Bundle
import android.os.TransactionTooLargeException
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R
import com.glimpse.glimpse.data.SavedBeacon
import com.glimpse.glimpse.manager.SavedBeaconsManager
import com.glimpse.glimpse.ui.BeaconListCard
import com.glimpse.glimpse.ui.SavedBeaconCard
import com.glimpse.glimpse.util.GlimpseTools
import com.glimpse.glimpse.util.SavedBeaconsListAdapter
import kotlinx.android.synthetic.main.fragment_nearby_beacons.*

class SavedBeacons : Fragment() {

    private lateinit var savedBeaconsManager : SavedBeaconsManager
    private lateinit var savedBeaconsRecyclerView: RecyclerView
    private lateinit var savedBeaconsListAdapter : RecyclerView.Adapter<*>
    private lateinit var savedBeaconsViewManager : RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_saved_beacons, container, false)
    }

    override fun onStart() {
        super.onStart()

        savedBeaconsManager = SavedBeaconsManager(requireActivity())

        var savedBeacons = savedBeaconsManager.getAllSavedBeacons()

        savedBeaconsViewManager = LinearLayoutManager(requireView().context)
        savedBeaconsListAdapter = SavedBeaconsListAdapter(savedBeacons)

        // if at least one saved beacon, hide text

        savedBeaconsRecyclerView = requireView().findViewById(R.id.savedBeaconListRecyclerView)
        savedBeaconsRecyclerView?.apply {
            setHasFixedSize(true)
            adapter = savedBeaconsListAdapter
            layoutManager = savedBeaconsViewManager
        }

        clearItemDecorations()
        savedBeaconsRecyclerView.addItemDecoration(SavedBeaconCard.CardDivider())
    }

    private fun clearItemDecorations() {
        while (savedBeaconsRecyclerView.itemDecorationCount > 0) {
            savedBeaconsRecyclerView.removeItemDecorationAt(0)
        }
    }
}