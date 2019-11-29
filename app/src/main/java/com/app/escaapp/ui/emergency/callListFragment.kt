package com.app.escaapp.ui.emergency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.app.escaapp.R
import com.app.escaapp.ui.manage.ListAdapter
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import com.example.management.savehistoryModel
import kotlinx.android.synthetic.main.fragment_call_list.view.*
import kotlinx.android.synthetic.main.navbar_botton.view.*

class callListFragment : Fragment() {
    lateinit var db: UsersDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = UsersDBHelper(requireContext())
        return inflater.inflate(R.layout.fragment_call_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(view)

        val users = db.getAllUser()
        view.dynamic_call_list.adapter =  ListAdapter(requireActivity(),R.layout.user_customview,users)

        view.dynamic_call_list.setOnItemClickListener { adapterView, view, position, id ->
            val item = adapterView.getItemAtPosition(position) as UserModel
            Toast.makeText(requireContext(), "Click on item at ${item.relate_name} id -> ${item.id} ", Toast.LENGTH_LONG).show()
            db.saveHistory(savehistoryModel(0,item.phone_no,0.0,0.0))

//                    callTo(item.phone_no  )
        }

        view.nav_emergency.setOnClickListener {
            view.findNavController().popBackStack()
        }

    }

    fun init(view:View){
        view.nav_profile.visibility = View.GONE
        view.nav_location.visibility = View.GONE
        view.nav_manage.visibility = View.GONE
        view.nav_setting.visibility = View.GONE

        view.nav_emergency.setBackgroundResource(R.drawable.return_home)
    }
}