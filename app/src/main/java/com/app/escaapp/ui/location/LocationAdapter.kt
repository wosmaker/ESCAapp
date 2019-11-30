package com.app.escaapp.ui.location

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.app.escaapp.R
import com.app.escaapp.db.LocationModel
import kotlinx.android.synthetic.main.field.view.*


class LocationAdapter(val activity: Activity) : RecyclerView.Adapter<LocationAdapter.LocationViewHolder>() {
    private var datasource = ArrayList<LocationModel>()
    private var oldList = ArrayList<LocationModel>()
    private var deleteQ = ArrayList<LocationModel>()

    var edit_mode = false

    class LocationViewHolder(val activity: Activity, itemView: View) : RecyclerView.ViewHolder(itemView) {

        //val edit_location = itemView.edit_location
        val locationName = itemView.location_edtxt!!
        val location = itemView.location_edtxt2!!
        val deleteButton = itemView.delete_btn!!
        val icon = itemView.iconLocation
        val bt_view = itemView.bt_view

    }

    override fun getItemCount(): Int = datasource.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder =
            LayoutInflater.from(parent.context)
                    .inflate(R.layout.field, parent, false)
                    .run {
                        LocationViewHolder(activity, this)
                    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val location = datasource[position]

        holder.run{
            locationName.text = location.name
            this.location.text = location.location
            //phone_no.text = location.phone_no
            if (edit_mode) {
                deleteButton.visibility = View.VISIBLE
                bt_view.isEnabled = false
                icon.isEnabled = false

            } else {
                deleteButton.visibility = View.INVISIBLE
                bt_view.isEnabled = true
                icon.isEnabled = true
            }
            bt_view.setOnClickListener {
                val gmmIntentUri: Uri = Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                activity.startActivity(mapIntent)
            }

            icon.setOnClickListener{
                val gmmIntentUri: Uri = Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                activity.startActivity(mapIntent)
            }

           deleteButton.setOnClickListener {
                deleteQ.add(location)
                datasource.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, datasource.size)

            }
        }
    }

    fun insertItem(newList: ArrayList<LocationModel>) {
        val diffUtil = locationDiffUtilCallback(datasource, newList)
        val diffResult: DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)

        Toast.makeText(activity, "DiffUtil --> $diffResult", Toast.LENGTH_LONG)
        datasource.clear()
        datasource.addAll(newList) // Add new item to exist list
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
        deleteQ.clear()
    }

    fun getDelete(): ArrayList<LocationModel> {
        return deleteQ
    }

    fun addItem(location: LocationModel) {
        datasource.add(location)
        notifyDataSetChanged()
    }

    fun backupOldList() {
        oldList = datasource.clone() as ArrayList<LocationModel>
    }

    fun restoreOldList() {
        datasource = oldList.clone() as ArrayList<LocationModel>
        oldList.clear()
        deleteQ.clear()
        notifyDataSetChanged()
    }


}
