package com.glimpse.glimpse.util

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.glimpse.glimpse.fragments.GlimpseSites
import com.glimpse.glimpse.manager.SiteManager
import com.glimpse.glimpse.ui.SiteListCard
import kotlinx.android.synthetic.main.fragment_glimpse_sites.view.*

class SiteListAdapter(private val glimpseSites : GlimpseSites, private val siteManager : SiteManager) : RecyclerView.Adapter<SiteListAdapter.SiteListViewHolder>() {

    class SiteListViewHolder(val siteCard : SiteListCard) : RecyclerView.ViewHolder(siteCard)

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SiteListViewHolder {
        val card = SiteListCard(parent.siteListRecyclerView, parent.context)
        return SiteListViewHolder(card)
    }

    // replace the contents of a view
    override fun onBindViewHolder(holder: SiteListViewHolder, position: Int) {
        var sites = siteManager.sites()
        holder.siteCard.siteNameTextView.text = sites[position].name
        holder.siteCard.siteNameTextView.text = "Espers Glimpse Site"
        holder.siteCard.siteURLTextView.text = sites[position].url
        holder.siteCard.siteDateAddedTextView.text = sites[position].date
        holder.siteCard.menu.setOnMenuItemClickListener { glimpseSites.onMenuItemClick(it, holder.siteCard) }
        holder.siteCard.enableSwitch.isChecked = sites[position].enabled
        holder.siteCard.enableSwitch.setOnClickListener { glimpseSites.onSiteEnableToggle(holder.siteCard) }
    }

    override fun getItemCount(): Int {
        return siteManager.sites().size
    }

}