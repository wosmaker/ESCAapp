package com.app.escaapp.ui.setting

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.escaapp.R
import kotlinx.android.synthetic.main.fragment_location.view.*
import kotlinx.android.synthetic.main.fragment_setting.view.*


class SettingFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_setting, container, false)

        view.textView.text = "Hello world setting page"

        return view
    }

}
