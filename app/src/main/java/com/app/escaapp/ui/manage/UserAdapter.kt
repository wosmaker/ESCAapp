package com.app.escaapp.ui.manage

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
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.call_history_customview.view.*
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.user_customview.view.*

class UserAdapter(val activity: Activity):RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private var datasource = ArrayList<UserModel>()
    private var oldList = ArrayList<UserModel>()
    private var deleteQ = ArrayList<UserModel>()

    var edit_mode = false

    class UserViewHolder(val activity: Activity,itemView :View) : RecyclerView.ViewHolder(itemView) {

        val relate_name = itemView.relate_name
        val phone_no = itemView.phone_no
        val btn_delete = itemView.btn_delete

    }

    override fun getItemCount(): Int  = datasource.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.user_customview,parent,false)
            .run{
                UserViewHolder(activity ,this)
            }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = datasource[position]

        holder.relate_name.text = user.relate_name
        holder.phone_no.text = user.phone_no
        if (edit_mode){
            holder.btn_delete.visibility = View.VISIBLE
        }
        else{
            holder.btn_delete.visibility = View.INVISIBLE
        }

        holder.btn_delete.setOnClickListener {
            deleteQ.add(user)
            datasource.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position,datasource.size)

        }

    }

    fun insertItem(newList : ArrayList<UserModel>){
        val diffUtil = userDiffUtilCallback(datasource,newList)
        val diffResult:DiffUtil.DiffResult = DiffUtil.calculateDiff(diffUtil)

        Toast.makeText(activity,"DiffUtil --> $diffResult",Toast.LENGTH_LONG)
        datasource.clear()
        datasource.addAll(newList) // Add new item to exist list
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
        deleteQ.clear()
    }

    fun getDelete(): ArrayList<UserModel>{
        return deleteQ
    }

    fun addItem(user : UserModel){
        datasource.add(user)
        notifyDataSetChanged()
    }

    fun backupOldList(){
        oldList = datasource.clone() as ArrayList<UserModel>
    }

    fun restoreOldList(){
        datasource = oldList.clone() as ArrayList<UserModel>
        oldList.clear()
        deleteQ.clear()
        notifyDataSetChanged()
    }

}
