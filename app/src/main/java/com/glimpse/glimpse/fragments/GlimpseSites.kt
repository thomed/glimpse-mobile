package com.glimpse.glimpse.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R
import com.glimpse.glimpse.manager.SiteManager
import com.glimpse.glimpse.ui.SiteListCard
import com.glimpse.glimpse.ui.SiteUrlDialog
import com.glimpse.glimpse.util.GlimpseTools
import com.glimpse.glimpse.util.SiteListAdapter

class GlimpseSites : Fragment(), DialogInterface.OnDismissListener {


    private lateinit var siteManager : SiteManager
    private lateinit var siteListRecyclerView : RecyclerView
    private lateinit var siteListViewAdapter : RecyclerView.Adapter<*>
    private lateinit var siteListViewManager : RecyclerView.LayoutManager
    private lateinit var glimpseTools : GlimpseTools
    private var siteUrlDialog = SiteUrlDialog(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_glimpse_sites, container, false)
    }

    override fun onStart() {
        super.onStart()

        glimpseTools = GlimpseTools(requireActivity())
        siteManager = SiteManager(requireActivity())

        siteListViewManager = LinearLayoutManager(view?.context)
        siteListViewAdapter = SiteListAdapter(this, siteManager)

        siteListRecyclerView = requireView().findViewById(R.id.siteListRecyclerView)
        siteListRecyclerView?.apply {
            setHasFixedSize(true)
            adapter = siteListViewAdapter
            layoutManager = siteListViewManager
        }

        clearItemDecorations()
        siteListRecyclerView.addItemDecoration(SiteListCard.CardDivider())
        registerForContextMenu(siteListRecyclerView)

        initializeClickHandlers()
    }

    /**
     * Tell the buttons on the sites page what to do.
     */
    private fun initializeClickHandlers() {
        view?.findViewById<Button>(R.id.openUrlDialog)?.setOnClickListener {
            var fm = requireFragmentManager()
            siteUrlDialog.show(fm, "fragment_site_url_dialog")
        }
    }

    /**
     * Use to remove decorations from the recyclerview. Otherwise decorations stack whenever the
     * screen is toggled on/off.
     */
    private fun clearItemDecorations() {
        while (siteListRecyclerView.itemDecorationCount > 0) {
            siteListRecyclerView.removeItemDecorationAt(0)
        }
    }

    /**
     * Handle the menuItem clicks of the individual card popup menus.
     */
    fun onMenuItemClick(item: MenuItem?, card : SiteListCard): Boolean {
        print("Selected an item\n")
        return when(item?.itemId) {
            R.id.site_card_options_delete -> {
                siteManager.deleteURL(card.siteURLTextView.text.toString())
                siteListViewAdapter.notifyDataSetChanged()
                var toast = Toast.makeText(activity, "Deleted site: ${card.siteURLTextView.text}", Toast.LENGTH_LONG)
                toast.setGravity(Gravity.CENTER, 0, 0)
                toast.show()
                true
            }
            R.id.site_card_options_edit -> {
                true
            }
            else -> false
        }
    }

    fun onSiteEnableToggle(card : SiteListCard) {
        siteManager.updateEnabled(card.url(), card.enableSwitch.isChecked)
    }

    /**
     * Handle the close of the SiteUrlDialog.
     */
    override fun onDismiss(dialog: DialogInterface?) {
        if (siteUrlDialog.positive) {
            Log.d("SITES_FRAGMENT", "Getting dialog value: ${siteUrlDialog.urlString}")

            // TODO Test the URL for valid glimpse server
            siteManager.insertURL(siteUrlDialog.urlString)
        }
    }}

