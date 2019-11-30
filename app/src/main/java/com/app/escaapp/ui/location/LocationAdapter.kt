package com.app.escaapp.ui.location

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.escaapp.R
import com.app.escaapp.db.LocationModel

import kotlinx.android.synthetic.main.call_history_customview.view.*
import kotlinx.android.synthetic.main.field.view.*
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.user_customview.view.*

class LocationAdapter(val activity: Activity):RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    private var datasource = ArrayList<LocationModel>()
    private var oldList = ArrayList<LocationModel>()
    private var deleteQ = ArrayList<LocationModel>()

    var edit_mode = false

    class LocationViewHolder(val activity: Activity,itemView :View) : RecyclerView.ViewHolder(itemView) {

        //val edit_location = itemView.edit_location
        val edit_text = itemView.location_edtxt
        val delete_button = itemView.delete_btn

    }

    override fun getItemCount(): Int  = datasource.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.field,parent,false)
                    .run{
                        LocationViewHolder(activity ,this)
                    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = datasource[position]

        holder.edit_text.text = location.name
       // holder.phone_no.text = location.phone_no
        if (edit_mode){
            holder.delete_button.visibility = View.VISIBLE
        }
        else{
            holder.delete_button.visibility = View.INVISIBLE
        }

       holder.delete_button.setOnClickListener {
            deleteQ.add(location)
            datasource.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,datasource.size)

        }

    }

    fun insertItem(newList : ArrayList<LocationModel>){
        val diffUtil = locationDiffUtilCallback(datasource,newList)
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)

        Toast.makeText(activity,"DiffUtil --> $diffResult",Toast.LENGTH_LONG)
        datasource.clear()
        datasource.addAll(newList) // Add new item to exist list
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
        deleteQ.clear()
    }

    fun getDelete(): ArrayList<LocationModel>{
        return deleteQ
    }

    fun addItem(location : LocationModel){
        datasource.add(location)
        notifyDataSetChanged()
    }

    fun backupOldList(){
        oldList = datasource.clone() as ArrayList<LocationModel>
    }

    fun restoreOldList(){
        datasource = oldList.clone() as ArrayList<LocationModel>
        oldList.clear()
        deleteQ.clear()
        notifyDataSetChanged()
    }



}
