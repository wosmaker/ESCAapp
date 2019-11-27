package com.app.escaapp.ui.manage

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.app.escaapp.R
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.call_history_customview.view.*
import kotlinx.android.synthetic.main.user_customview.view.*

class UserAdapter(val activity: Activity, private val list: ArrayList<UserModel>):RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder =
        LayoutInflater.from(parent.context)
            .inflate(R.layout.user_customview,parent,false)
            .run{
                UserViewHolder(activity ,this)
            }


    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size
}


class UserViewHolder(val activity: Activity,itemView :View) : RecyclerView.ViewHolder(itemView) {

    fun bind(user: UserModel) {
        itemView.relate_name.text = user.relate_name
        itemView.phone_no.text = user.phone_no

        itemView.btn_delete.setOnClickListener {
            Toast.makeText(activity, "Hellow test ", Toast.LENGTH_LONG).show()

        }

    }
}