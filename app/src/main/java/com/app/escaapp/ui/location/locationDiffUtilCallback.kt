package com.app.escaapp.ui.location

import androidx.recyclerview.widget.DiffUtil
import com.app.escaapp.db.LocationModel
import com.example.management.UserModel

class locationDiffUtilCallback(private val oldList:ArrayList<LocationModel>, private val newList : ArrayList<LocationModel>) :DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldItemPosition == newItemPosition

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}