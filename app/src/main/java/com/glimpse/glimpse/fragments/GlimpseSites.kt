package com.glimpse.glimpse.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.R
import com.glimpse.glimpse.manager.SiteManager
import com.glimpse.glimpse.util.SiteListAdapter

class GlimpseSites : Fragment() {

    private lateinit var siteManager : SiteManager
    private lateinit var siteListRecyclerView : RecyclerView
    private lateinit var siteListViewAdapter : RecyclerView.Adapter<*>
    private lateinit var siteListViewManager : RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.glimpse_sites, container, false)
    }

    override fun onStart() {
        super.onStart()

        siteManager = SiteManager(requireActivity())

        siteListViewManager = LinearLayoutManager(view?.context)
//        siteListViewAdapter = SiteListAdapter()

        siteListRecyclerView = view!!.findViewById<RecyclerView>(R.id.siteListRecyclerView)
        siteListRecyclerView?.apply {
            setHasFixedSize(true)
//            adapter = siteListViewAdapter
            layoutManager = siteListViewManager
        }

        // TODO simple tests for db insertion and retrieval, remove when not needed
        siteManager.insertURL("THIS IS A TEST URL IN THE DB")
        siteManager.insertURL("https://google.com/glimpse")
        siteManager.urls().forEach {
            Log.d("DB_URL", "Found: $it")
        }
    }
}