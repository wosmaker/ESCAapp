package com.app.escaapp.ui.profile

import android.content.Context

import android.os.Bundle
import android.os.Handler
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.findNavController
import com.app.escaapp.NavBar
import com.app.escaapp.R
import com.app.escaapp.popup
import com.app.escaapp.ui.setting.SettingFragment
import kotlinx.android.synthetic.main.fragment_emergency.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import java.lang.Exception
import java.util.*
import kotlin.concurrent.schedule


class ProfileFragment : Fragment() {

    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        NavBar().setGo(0, view)
        val dialog = popup().notInclude(requireContext())

        Timer().schedule(3000) {
            dialog.dismiss()
            view.findNavController().popBackStack()
        }
    }

}
