package com.app.escaapp.ui.emergency


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_emergency.view.*

/**
 * A simple [Fragment] subclass.
 */
class EmergencyFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_emergency, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.test1.text = "Hello Emergency"
    }

}
