package com.glimpse.glimpse.fragments

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.Rect
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R
import com.glimpse.glimpse.data.Site
import com.glimpse.glimpse.manager.BeaconManager
import com.glimpse.glimpse.manager.RequestManager
import com.glimpse.glimpse.ui.BeaconListCard
import com.glimpse.glimpse.util.BeaconListAdapter

class NearbyBeacons : Fragment() {

    private lateinit var currentContext: Context
    private lateinit var beaconManager: BeaconManager
    private lateinit var requestManager: RequestManager
    private lateinit var enabledBeaconNames : HashMap<String, Site>
    private lateinit var beaconListRecyclerView : RecyclerView
    private lateinit var beaconListViewAdapter: RecyclerView.Adapter<*>
    private lateinit var beaconListViewManager : RecyclerView.LayoutManager
    private val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    /**
     * Event handler for when a bluetooth device is discovered
     */
    private val bReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            Log.d("NEARBY_BEACONS", "Bluetooth receiver receiving")

            val action = intent.action

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                var device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as BluetoothDevice?

                if (device != null && device.name != null && enabledBeaconNames.containsKey(device.name)) {
                    Log.d("BT_DISCOVER", "Discovered BT device: " + device.name)
//                    foundBeacon(device, enabledBeaconNames[device.name]!!)
                    beaconManager.getBeaconsByDevice(device, ::updateViewForBeacons)
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                // TODO have this be a setting that the user can change
                // continuously be discovering
                btAdapter?.startDiscovery()
            }
        }
    }

    /**
     * Clear the view and populate it with elements representing current beacons
     */
    fun updateViewForBeacons() {
        Log.d("NEARBY_BEACONS", "Called updated view for beacons.")
        beaconListViewAdapter.notifyDataSetChanged()
//        view?.findViewById<LinearLayout>(R.id.beaconListScrollVerticalLayout)?.removeAllViews()
//        beaconManager.beacons.forEach {
//            var mapEntry = it
//            mapEntry.value.forEach {
//                view?.findViewById<LinearLayout>(R.id.beaconListScrollVerticalLayout)?.addView(it.listBtn)
//            }
//        }
    }

    /**
     * Handles the event when this fragment is attached to an activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentContext = context
        beaconManager = BeaconManager(requireActivity())
        requestManager = RequestManager(context)
        enabledBeaconNames = beaconManager.enabledDevices
    }

    override fun onDestroy() {
        btAdapter?.cancelDiscovery()
        currentContext.unregisterReceiver(bReceiver)
        super.onDestroy()
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()

        // TODO
        // startActivityForResult is async so does not wait for bluetooth to be enabled
        // this means the activity has to be restarted to work properly if bt isn't enabled.
        if (btAdapter != null) {
            if (!btAdapter.isEnabled) {
                startActivityForResult(Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1)
            }

            val btFilter = IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)
            btFilter.addAction(BluetoothDevice.ACTION_FOUND)
            currentContext.registerReceiver(bReceiver, btFilter)
            btAdapter.startDiscovery()
            Log.d("NEARBY_BEACONS", "Bluetooth adapter started discovering")
        } else {
            Toast.makeText(currentContext, "Unable to get a bluetooth adapter.", Toast.LENGTH_LONG).show()
        }

        beaconListViewManager = LinearLayoutManager(view?.context)
        beaconListViewAdapter = BeaconListAdapter(beaconManager)

        beaconListRecyclerView = requireView().findViewById(R.id.beaconListRecyclerView)
        beaconListRecyclerView?.apply {
            setHasFixedSize(true)
            adapter = beaconListViewAdapter
            layoutManager = beaconListViewManager
        }

        beaconListRecyclerView.addItemDecoration(BeaconListCard.CardDivider())

    }

    /**
     * Handles the event when this fragment's view is requested
     *
     * Creates the view from the fragment_nearby_beacons.xmlacons.xml layout file
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_nearby_beacons, container, false)
    }


    /**
     * Check for bluetooth permissions etc.
     * Request if not already granted.
     */
    private fun checkPermissions() {
        val granted = PackageManager.PERMISSION_GRANTED

        if (ContextCompat.checkSelfPermission(this.currentContext, Manifest.permission.BLUETOOTH) != granted
            || ContextCompat.checkSelfPermission(this.currentContext, Manifest.permission.BLUETOOTH_ADMIN) != granted
            || ContextCompat.checkSelfPermission(this.currentContext, Manifest.permission.ACCESS_COARSE_LOCATION) != granted) {

            ActivityCompat.requestPermissions(
                this.requireActivity(), arrayOf(
                    Manifest.permission.BLUETOOTH,
                    Manifest.permission.BLUETOOTH_ADMIN,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), 1337
            )
        }
    }



}
