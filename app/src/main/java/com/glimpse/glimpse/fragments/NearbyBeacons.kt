package com.glimpse.glimpse.fragments

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
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
import com.glimpse.glimpse.R
import com.glimpse.glimpse.manager.BeaconManager
import com.glimpse.glimpse.manager.RequestManager

class NearbyBeacons : Fragment() {

    lateinit var currentContext: Context
    lateinit var beaconManager: BeaconManager
    lateinit var requestManager: RequestManager
    private val btAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()

    /**
     * Event handler for when a bluetooth device is discovered
     */
    private val bReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            Log.d("NEARBY_BEACONS", "Bluetooth receiver receiving")

            val action = intent.action

            Log.d("NEARBY_BEACONS", "Bluetooth receive action: " + action.toString())

            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                var device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE) as BluetoothDevice?

                if (device != null && device.name != null) {
                    Log.d("BT_DISCOVER", "Discovered BT device: " + device.name)
                    foundBeacon(device)
                }
            }
        }
    }

    /**
     * Adds a bluetoothdevice to the beaconmanager and adds its button to the UI
     *
     * TODO
     * In the future the beaconmanager will perform some verification.
     * For now, all bluetooth devices are being added
     */
    private fun foundBeacon(device: BluetoothDevice) {
        view?.findViewById<LinearLayout>(R.id.beaconListScrollVerticalLayout)?.removeAllViews()
        beaconManager.addBeacon(device)
        beaconManager.beacons.forEach {
            view?.findViewById<LinearLayout>(R.id.beaconListScrollVerticalLayout)?.addView(
//                it.value.listBtn
                it.value.listBtn
            )
        }

        Log.d("FOUND_BEACON", "There are " + beaconManager.beacons.size + " beacons")
    }

    /**
     * Handles the event when this fragment is attached to an activity
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        currentContext = context
        beaconManager = BeaconManager(context)
        requestManager = RequestManager(context)
    }

    override fun onDestroy() {
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
            // no bluetooth adapter available
            Toast.makeText(currentContext, "Unable to get a bluetooth adapter.", Toast.LENGTH_LONG)
        }

        // TODO
        // Remove this. It's just a test to get some JSON.
        requestManager.getJSONfromURL("")

    }

    /**
     * Handles the event when this fragment's view is requested
     *
     * Creates the view from the nearby_beacons.xml layout file
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.nearby_beacons, container, false)
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
