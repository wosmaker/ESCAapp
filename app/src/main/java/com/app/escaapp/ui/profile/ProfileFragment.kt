package com.app.escaapp.ui.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.component1
import com.app.escaapp.FirstTime
import com.app.escaapp.R
import com.app.escaapp.ui.location.LocationFragment
import kotlinx.android.synthetic.main.activity_main_app.*
import kotlinx.android.synthetic.main.activity_main_app.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.android.synthetic.main.navbar_botton.view.*


class ProfileFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        val view = inflater!!.inflate(R.layout.fragment_profile, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }


}
