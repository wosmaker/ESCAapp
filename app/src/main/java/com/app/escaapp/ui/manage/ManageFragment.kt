package com.app.escaapp.ui.manage


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.marginTop
import androidx.navigation.findNavController
import com.app.escaapp.IOBackPressed
import com.app.escaapp.NavBar
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_manage.view.*


class ManageFragment : Fragment(), IOBackPressed {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_manage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        NavBar().setGo(3, view)
        Edit_state(view)
    }

    override fun onBackPressed(): Boolean {

    }

    private fun Edit_state(view:View){
        view.Edit.setOnClickListener {Start_Anime(view)}
        view.Cancel.setOnClickListener{End_Anime(view)}
        view.Done.setOnClickListener{End_Anime((view))}
        view.Add.setOnClickListener {
            view.findNavController().navigate()

        }
    }

    private fun Start_Anime(view:View){
        val fade = AnimationUtils.loadAnimation(requireContext(), R.anim.fade)
        val sd = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down)
        val sd_add = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in)

        view.block.startAnimation(sd)
        view.block.translationY = 30F

        view.Add.startAnimation(sd_add)
        view.Done.startAnimation(sd_add)
        view.Cancel.startAnimation(sd_add)

        view.Edit.startAnimation(fade)

        view.Cancel.visibility = View.VISIBLE
        view.Done.visibility = View.VISIBLE
        view.Add.visibility = View.VISIBLE

        view.Edit.visibility = View.INVISIBLE
    }

    private fun End_Anime(view: View){
        val fade = AnimationUtils.loadAnimation(activity, R.anim.fade_in)
        val sd = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
        val sd_add = AnimationUtils.loadAnimation(activity,R.anim.fade)

        view.block.translationY = 0F
        view.block.startAnimation(sd)

        view.Add.startAnimation(sd_add)
        view.Cancel.startAnimation(sd_add)
        view.Done.startAnimation(sd_add)

        view.Edit.visibility = View.VISIBLE
        view.Cancel.visibility = View.INVISIBLE
        view.Done.visibility = View.INVISIBLE
        view.Add.visibility = View.INVISIBLE
    }



}
