package com.app.escaapp.ui.emergency

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.escaapp.R
import com.app.escaapp.ui.manage.UserAdapter
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_call_history.view.*
import kotlinx.android.synthetic.main.navbar_botton.view.*
import kotlinx.android.synthetic.main.popup_deleteall.view.*

class historyFragment :Fragment(){
    lateinit var db: UsersDBHelper
    lateinit var adaptor : historyRecycleView

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

        adaptor = historyRecycleView(requireActivity())
        adaptor.insertItem(db.getAllHistory())
        val Adapter_ =  UserAdapter(requireActivity())
        Adapter_.insertItem(db.getAllCustom())

        Log.d("testdb","test history -> ${db.getAllHistory()}")

        view.run{

            dynamic_historyCall.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = adaptor
            }

            nav_emergency.setOnClickListener {
                view.findNavController().popBackStack()
            }

            deleteAll.setOnClickListener {
                val popView =
                    LayoutInflater.from(requireContext()).inflate(R.layout.popup_deleteall, null)
                val dialog = AlertDialog.Builder(requireContext())
                    .setView(popView)
                    .setCancelable(false)
                    .create()
                dialog.show()
                popView.run {
                    Yes.setOnClickListener {
                        db.deleteAllHistory()
                        adaptor.insertItem(db.getAllHistory())
                        Toast.makeText(requireContext(), "Clear all history ", Toast.LENGTH_LONG)
                        dialog.dismiss()
                    }
                    No.setOnClickListener {
                        dialog.dismiss()
                    }
                }
            }

        }

    }


    private fun init(view:View){
        view.nav_profile.visibility = View.GONE
        view.nav_location.visibility = View.GONE
        view.nav_manage.visibility = View.GONE
        view.nav_setting.visibility = View.GONE

        view.nav_emergency.setBackgroundResource(R.drawable.return_home)
    }
}