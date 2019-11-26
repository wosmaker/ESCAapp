package com.app.escaapp.ui.emergency

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.app.escaapp.R
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.user_customview.view.*

class callListAdaptor(activity: Activity,resource : Int = R.layout.call_list_customview,items:ArrayList<UserModel>) : ArrayAdapter<UserModel>(activity,resource,items){

    private val resource:Int
    private val list : ArrayList<UserModel>
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
        rowView.relate_name.text = list[position].phone_no

        return rowView

    }
}