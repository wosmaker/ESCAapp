package com.app.escaapp.ui.manage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_manage.view.*


class ManageFragment : Fragment() {

    private lateinit var viewModel: MangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        when_edit_cancel(view)
        add_contact(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MangeViewModel::class.java)
    }


    private fun when_edit_cancel(view:View){
        view.Edit.setOnClickListener {
            view.Cancel.visibility = View.VISIBLE
            view.Done.visibility = View.VISIBLE
            view.Add.visibility = View.VISIBLE

            view.Edit.visibility = View.INVISIBLE
        }

        view.Cancel.setOnClickListener{
            view.Edit.visibility = View.VISIBLE

            view.Cancel.visibility = View.INVISIBLE
            view.Done.visibility = View.INVISIBLE
            view.Add.visibility = View.INVISIBLE
            // Do action here

        }

        view.Done.setOnClickListener{
            view.Edit.visibility = View.VISIBLE

            view.Cancel.visibility = View.INVISIBLE
            view.Done.visibility = View.INVISIBLE
            view.Add.visibility = View.INVISIBLE
            // Do action here

        }
    }


    private fun add_contact(view:View){
        view.Add.setOnClickListener{
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, ManageFragment_contact())
                //.addToBackStack(null)
                .commit()
        }
    }


}
