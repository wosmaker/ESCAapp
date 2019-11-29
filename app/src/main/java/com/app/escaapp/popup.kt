package com.app.escaapp

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.popup_notinclude.view.*

class popup {

    fun notInclude(context : Context):AlertDialog{
        val popView = LayoutInflater.from(context).inflate(R.layout.popup_notinclude,null)
        val dialog = AlertDialog.Builder(context)
            .setView(popView)
            .create()

        dialog.show()
        popView.exit.setOnClickListener{
            dialog.dismiss()
        }
        return dialog
    }

}