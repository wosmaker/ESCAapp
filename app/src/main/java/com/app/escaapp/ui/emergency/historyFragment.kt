package com.app.escaapp.ui.emergency

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.app.escaapp.R
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_call_history.view.*
import kotlinx.android.synthetic.main.navbar_botton.view.*

class historyFragment :Fragment(){
    lateinit var db: UsersDBHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = UsersDBHelper(requireContext())
        return inflater.inflate(R.layout.fragment_call_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(view)

        view.nav_emergency.setOnClickListener {
            view.findNavController().popBackStack()
        }

        view.dynamic_historyCall.adapter = historyAdapter(requireActivity(),R.layout.call_history_customview,db.getAllHistory())

    }

    fun init(view:View){
        view.nav_profile.visibility = View.GONE
        view.nav_location.visibility = View.GONE
        view.nav_manage.visibility = View.GONE
        view.nav_setting.visibility = View.GONE

        view.nav_emergency.setBackgroundResource(R.drawable.return_home)
    }
}