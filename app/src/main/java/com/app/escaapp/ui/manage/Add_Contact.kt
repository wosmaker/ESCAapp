package com.app.escaapp.ui.manage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_manage.*
import kotlinx.android.synthetic.main.fragment_manage.view.*

class Add_Contact : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_manage_contact, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        cancel_input(view)
    }

    private fun cancel_input(view : View){
        view.Cancel.setOnClickListener{
            //do here

            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.master_page, ManageFragment())
                //.addToBackStack(null)
                .commit()
        }
    }

    private fun done_input(view : View){
        view.Done.setOnClickListener{
            // do here

            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.master_page, ManageFragment())
                //.addToBackStack(null)
                .commit()
        }
    }

}