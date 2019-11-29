package com.app.escaapp.ui.emergency

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.escaapp.R
import com.example.management.historyModel
import kotlinx.android.synthetic.main.call_history_customview.view.*

class historyRecycleView(val activity: Activity) : RecyclerView.Adapter<historyRecycleView.vvholder>() {

    private var datasource = ArrayList<historyModel>()

    class vvholder(itemView : View): RecyclerView.ViewHolder(itemView){
        val name = itemView.name
        val datetime = itemView.time
        val phone = itemView.phone
    }

    override fun getItemCount(): Int = datasource.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): vvholder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.call_history_customview,parent,false)
            .run {
                vvholder(this)
            }

    override fun onBindViewHolder(holder: vvholder, position: Int) {
        val history = datasource[position]
        holder.run{
            name.text = history.name
            datetime.text = history.datetime
            phone.text = history.desPhone
        }
    }

    fun insertItem(newList : ArrayList<historyModel>){
        datasource.clear()
        datasource = newList.clone() as ArrayList<historyModel>
        notifyDataSetChanged()
    }

}