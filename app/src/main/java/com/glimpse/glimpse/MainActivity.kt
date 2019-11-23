package com.glimpse.glimpse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.glimpse.glimpse.fragments.NearbyBeacons
import com.glimpse.glimpse.fragments.PinnedBeacons
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
                R.id.beacons -> {
                    Log.d("DRAWER", "Selected beacons")
                    this.title = "Glimpse - Nearby Beacons"
                    changeView(NearbyBeacons())
                    true
                }

                R.id.pinned -> {
                    Log.d("DRAWER", "Selected pinned")
                    this.title = "Glimpse - Pinned Beacons"
                    changeView(PinnedBeacons())
                    true
                }

                R.id.recent -> {
                    Log.d("DRAWER", "Selected recent")
                    true
                }

                R.id.sites -> {
                    Log.d("DRAWER", "Selected sites")
                    true
                }

                R.id.settings -> {
                    Log.d("DRAWER", "Selected settings")
                    true
                }

                else -> false
            }
        }

        changeView(NearbyBeacons())

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // this makes the drawer slide out
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * Parameter is the resource id of the fragment
     */
    fun changeView(frag : Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.main_frame, frag).commit()
    }

}
