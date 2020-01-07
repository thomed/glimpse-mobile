package com.glimpse.glimpse.util

import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.manager.SiteManager

class SiteListAdapter() : RecyclerView.Adapter<SiteListAdapter.SiteListViewHolder>(){

    class SiteListViewHolder(val textView : TextView) : RecyclerView.ViewHolder(textView)

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteListViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    // replace the contents of a view
    override fun onBindViewHolder(holder: SiteListViewHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //return SiteManager.urls().size
    }

}