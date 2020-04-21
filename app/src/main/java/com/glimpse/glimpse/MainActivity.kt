package com.glimpse.glimpse

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.glimpse.glimpse.fragments.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

//    var fragmentManager : FragmentManager = supportFragmentManager

    lateinit var drawer : DrawerLayout
    lateinit var drawerToggle : ActionBarDrawerToggle
    lateinit var navView : NavigationView


    // https://medium.com/quick-code/android-navigation-drawer-e80f7fc2594f
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawer = findViewById(R.id.activity_main)
        drawerToggle = ActionBarDrawerToggle(this, drawer, R.string.Open, R.string.Close)
        navView = findViewById(R.id.nv)

        drawer.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // handle the different selections in the drawer
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    Log.d("DRAWER", "Selected home")
                    this.title = "Glimpse"
                    changeView(GlimpseHome())
                    true
                }

                R.id.beacons -> {
                    Log.d("DRAWER", "Selected beacons")
                    this.title = "Nearby Beacons"
                    changeView(NearbyBeacons())
                    true
                }

                R.id.pinned -> {
                    Log.d("DRAWER", "Selected pinned")
                    this.title = "Saved Beacons"
                    changeView(SavedBeacons())
                    true
                }

//                R.id.recent -> {
//                    Log.d("DRAWER", "Selected recent")
//                    this.title = "Glimpse - Recent Beacons"
//                    changeView(RecentBeacons())
//                    true
//                }

                R.id.sites -> {
                    Log.d("DRAWER", "Selected sites")
                    this.title = "Glimpse Sites"
                    changeView(GlimpseSites())
                    true
                }

//                R.id.settings -> {
//                    Log.d("DRAWER", "Selected settings")
//                    this.title = "Glimpse Settings"
//                    changeView(GlimpseSettings())
//                    true
//                }

                else -> false
            }
        }

        checkPermissions()

        // set the default/initial view fragment
        changeView(GlimpseHome())

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // this makes the drawer slide out
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Parameter is the constructor of the class that handles the fragment
     */
    private fun changeView(frag : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, frag).commit()
    }

    /**
     * Check for internet access permissions etc.
     * Request if not already granted.
     */
    private fun checkPermissions() {
        val granted = PackageManager.PERMISSION_GRANTED

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != granted) {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    Manifest.permission.INTERNET
                ), 9999
            )
        } else {
            Log.d("PERMISSION" , "Glimpse has internet permissions")
        }
    }


}
