package com.app.escaapp.ui.manage


import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ListAdapter
import android.widget.Toast
import androidx.core.view.marginTop
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.vvalidator.form
import com.afollestad.vvalidator.util.hide
import com.app.escaapp.IOBackPressed
import com.app.escaapp.MainAppActivity
import com.app.escaapp.NavBar
import com.app.escaapp.R
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_manage.*
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.*
import kotlinx.android.synthetic.main.pop_cancel_confirm.*
import kotlinx.android.synthetic.main.pop_cancel_confirm.view.*


class ManageFragment() : Fragment() {

    lateinit var db: UsersDBHelper
    lateinit var exview : View


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = UsersDBHelper(requireContext())
        return inflater.inflate(R.layout.fragment_manage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        exview = view

        NavBar().setGo(3, view)
        Edit_state(view)
        viewUser(view)


        view.Edit.setOnLongClickListener {
            val users = db.getAllUser()
            val customByUser = ArrayList<UserModel>()
            users.forEach {
                if (it.byUser){
                    customByUser.add(it)
                }
            }
            Toast.makeText(activity," user :: $customByUser",Toast.LENGTH_LONG).show()
            true
        }
    }

    override fun onResume() {
        viewUser(exview)
        super.onResume()

    }

    private fun viewUser(view:View){
        val users = db.getAllUser()
        val customByUser = ArrayList<UserModel>()
        users.forEach {
            if (it.byUser){
                customByUser.add(it)
            }
        }
        Toast.makeText(activity," user :: $customByUser",Toast.LENGTH_LONG).show()
        view.userListView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = UserAdapter(view,requireActivity(),customByUser)
        }
        view.userListView.adapter!!.notifyDataSetChanged()
    }

    private fun addContactData(view :View, builder: AlertDialog) {
        form{
            input(view.Name_){
                isNotEmpty().description("Please Input Contact Name ")
                length().atLeast(2)
                length().atMost(50)
            }

            input(view.Phone_){
                isNotEmpty().description("Please Input Phone Number")
                isNumber().description("Please user Number only")
                length().atLeast(2).description("Phone number at less 2")
                length().atMost(11).description("Phone number at most 10")

            }

            input(view.Relationship_){
                isNotEmpty().description("Please Input your relationship")
                length().atLeast(2)
                length().atMost(15)
            }

            view.btn_Cancel.setOnClickListener {
                val mDialog = LayoutInflater.from(requireContext()).inflate(R.layout.pop_cancel_confirm,null)
                val dialog = AlertDialog.Builder(requireContext())
                    .setView(mDialog)
                    .create()

                dialog.show()

                mDialog.Yes.setOnClickListener{
                    dialog.dismiss()
                    builder.dismiss()
                }

                mDialog.No.setOnClickListener{
                    dialog.dismiss()
                }
            }


            submitWith(view.btn_Add){
                try{
                    lateinit var user : UserModel
                    val id = 0
                    val relate_name = view.Name_.text.toString()
                    val phone_no = view.Phone_.text.toString()
                    val relation = view.Relationship_.text.toString()
                    val result = db.addUser(UserModel(id,relate_name,phone_no,relation,true))

                    Toast.makeText(activity,"Added user :: $result",Toast.LENGTH_LONG).show()
                    val count = exview.userListView.adapter!!.itemCount
                    exview.userListView.adapter!!.notifyItemInserted(count)
                    exview.userListView.adapter!!.notifyDataSetChanged()
                    builder.dismiss()
                }catch (e : java.lang.Exception){
                    Toast.makeText(activity,"Error :: $e",Toast.LENGTH_LONG).show()
                }
            }
        }

    }

    private fun error(e:Exception){
        Toast.makeText(requireContext(),"Error $e",Toast.LENGTH_LONG).show()
    }



    private fun Edit_state(view:View){
        view.Edit.setOnClickListener {Start_Anime(view)}
        view.Cancel.setOnClickListener{End_Anime(view)}
        view.Done.setOnClickListener{End_Anime((view))}
        view.Add.setOnClickListener {
            val mDialog = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_mange_addcontact,null)
            val mBuilder = AlertDialog.Builder(requireContext())
                .setView(mDialog)
                .create()

            mBuilder.show()
            addContactData(mDialog,mBuilder)
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
        view.Add.visibility = View.GONE
    }



}
