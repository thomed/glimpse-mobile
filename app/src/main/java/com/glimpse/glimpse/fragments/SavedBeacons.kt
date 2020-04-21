package com.glimpse.glimpse.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R
import com.glimpse.glimpse.data.SavedBeacon
import com.glimpse.glimpse.manager.SavedBeaconsManager
import com.glimpse.glimpse.ui.SavedBeaconCard
import com.glimpse.glimpse.util.SavedBeaconsListAdapter
import java.util.*

class SavedBeacons : Fragment() {

    private lateinit var savedBeaconsManager : SavedBeaconsManager
    private lateinit var savedBeaconsRecyclerView: RecyclerView
    private lateinit var savedBeaconsListAdapter : RecyclerView.Adapter<*>
    private lateinit var savedBeaconsViewManager : RecyclerView.LayoutManager
    private lateinit var savedBeacons : MutableList<SavedBeacon>

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

        savedBeacons = savedBeaconsManager.getAllSavedBeacons().toMutableList()

        savedBeaconsViewManager = LinearLayoutManager(requireView().context)
        savedBeaconsListAdapter = SavedBeaconsListAdapter(savedBeacons, requireActivity(), this)

//        savedBeaconsManager.maketest()

        // if at least one saved beacon, hide text
        if (savedBeacons.isNotEmpty()) {
            requireView().findViewById<TextView>(R.id.no_saved_message).visibility = View.GONE
        }

        savedBeaconsRecyclerView = requireView().findViewById(R.id.savedBeaconListRecyclerView)
        savedBeaconsRecyclerView?.apply {
            setHasFixedSize(true)
            adapter = savedBeaconsListAdapter
            layoutManager = savedBeaconsViewManager
        }

        clearItemDecorations()
        savedBeaconsRecyclerView.addItemDecoration(SavedBeaconCard.CardDivider())
    }

    fun updateSavedBeacons() {
        savedBeacons.clear()
        savedBeacons.addAll(savedBeaconsManager.getAllSavedBeacons())
        savedBeaconsListAdapter.notifyDataSetChanged()

        if (savedBeacons.isEmpty()) {
            requireView().findViewById<TextView>(R.id.no_saved_message).visibility = View.VISIBLE
        } else {
            requireView().findViewById<TextView>(R.id.no_saved_message).visibility = View.GONE
        }
    }

    private fun clearItemDecorations() {
        while (savedBeaconsRecyclerView.itemDecorationCount > 0) {
            savedBeaconsRecyclerView.removeItemDecorationAt(0)
        }
    }
}