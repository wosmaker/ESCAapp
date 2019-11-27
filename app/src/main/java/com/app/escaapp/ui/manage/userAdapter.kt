package com.app.escaapp.ui.manage

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.escaapp.R
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.user_customview.view.*




class userAdapter (activity: Activity, resource : Int = R.layout.user_customview , items:ArrayList<UserModel>):ArrayAdapter<UserModel>(activity,resource,items){

    private val resource:Int
    private var list: ArrayList<UserModel>
    private val vi : LayoutInflater
    private val activity : Activity
    private val db : UsersDBHelper

    init{
        this.resource = resource
        this.list = items
        this.vi = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this.activity = activity
        this.db = UsersDBHelper(activity)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = vi.inflate(resource,null,true)

        rowView.relate_name.text = list[position].relate_name
        rowView.phone_no.text = list[position].phone_no

        rowView.btn_delete.setOnClickListener {
            //            list.remove(list[position]
            db.deleteUser(list[position].id)
            notifyDataSetChanged()
            Toast.makeText(activity, "Item Delete", Toast.LENGTH_LONG).show()
        }
        return rowView
    }

}