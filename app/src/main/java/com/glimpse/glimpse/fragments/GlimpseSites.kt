package com.glimpse.glimpse.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R
import com.glimpse.glimpse.manager.SiteManager
import com.glimpse.glimpse.ui.SiteListCard
import com.glimpse.glimpse.ui.SiteUrlDialog
import com.glimpse.glimpse.util.SiteListAdapter

class GlimpseSites : Fragment() {

    private lateinit var siteManager : SiteManager
    private lateinit var siteListRecyclerView : RecyclerView
    private lateinit var siteListViewAdapter : RecyclerView.Adapter<*>
    private lateinit var siteListViewManager : RecyclerView.LayoutManager
//    private lateinit var textUrlDialog : AlertDialog;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_glimpse_sites, container, false)
    }

    override fun onStart() {
        super.onStart()

        siteManager = SiteManager(requireActivity())

        siteListViewManager = LinearLayoutManager(view?.context)
        siteListViewAdapter = SiteListAdapter(siteManager)

        siteListRecyclerView = view!!.findViewById<RecyclerView>(R.id.siteListRecyclerView)
        siteListRecyclerView?.apply {
            setHasFixedSize(true)
            adapter = siteListViewAdapter
            layoutManager = siteListViewManager
        }

        clearItemDecorations()
        siteListRecyclerView.addItemDecoration(SiteListCard.CardDivider())

        // TODO simple tests for db insertion and retrieval, remove when not needed
        siteManager.insertURL("THIS IS A TEST URL IN THE DB")
        siteManager.insertURL("https://google.com/glimpse")
        siteManager.sites().forEach {
            Log.d("DB_URL", "Found: $it")
        }

        initializeButtonHandlers()
    }

    private fun clearItemDecorations() {
        while (siteListRecyclerView.itemDecorationCount > 0) {
            siteListRecyclerView.removeItemDecorationAt(0)
        }
    }

    private fun initializeButtonHandlers() {
        view?.findViewById<Button>(R.id.openUrlDialog)?.setOnClickListener {
            var dialog = SiteUrlDialog()
            var fm = requireFragmentManager()
            dialog.show(fm, "fragment_site_url_dialog")
        }
    }
}