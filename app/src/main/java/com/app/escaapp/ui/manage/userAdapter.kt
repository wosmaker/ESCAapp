package com.app.escaapp.ui.manage

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.app.escaapp.R
import com.example.management.UserModel
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.user_customview.view.*




class userAdapter (activity: Activity, resource : Int = R.layout.user_customview , items:ArrayList<UserModel>):ArrayAdapter<UserModel>(activity,resource,items){

    var resource:Int
    var list: ArrayList<UserModel>
    var vi : LayoutInflater

    init{
        this.resource = resource
        this.list = items
        this.vi = activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val rowView = vi.inflate(resource,null,true)

        rowView.relate_name.text = list[position].relate_name
        rowView.phone_no.text = list[position].phone_no

        return rowView
    }

}