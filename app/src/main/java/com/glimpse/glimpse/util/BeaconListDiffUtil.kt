package com.glimpse.glimpse.util

import androidx.recyclerview.widget.DiffUtil
import com.glimpse.glimpse.data.Beacon

class BeaconListDiffUtil(var oldBeaconList : List<Beacon>, var newBeaconList : List<Beacon>) : DiffUtil.Callback() {


    /**
     * Called to check if two objects represent the same item
     */
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBeaconList[oldItemPosition] == newBeaconList[newItemPosition]
    }

    /**
     * Called to check if two items have the same data
     */
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldBeaconList[oldItemPosition].device.address == newBeaconList[newItemPosition].device.address
    }

    override fun getOldListSize(): Int {
        return oldBeaconList.size
    }

    override fun getNewListSize(): Int {
        return newBeaconList.size
    }

}