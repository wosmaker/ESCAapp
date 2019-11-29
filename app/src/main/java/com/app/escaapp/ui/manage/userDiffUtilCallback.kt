package com.app.escaapp.ui.manage

import androidx.recyclerview.widget.DiffUtil
import com.example.management.UserModel

class userDiffUtilCallback(private val oldList:ArrayList<UserModel>, private val newList : ArrayList<UserModel>) :DiffUtil.Callback(){

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = oldItemPosition == newItemPosition

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}