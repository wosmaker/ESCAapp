package com.app.escaapp.ui.manage


import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.vvalidator.form
import com.app.escaapp.NavBar
import com.app.escaapp.R
import com.example.management.UserModel
import com.example.management.UsersDBHelper
import kotlinx.android.synthetic.main.fragment_manage.view.*
import kotlinx.android.synthetic.main.fragment_mange_addcontact.view.*
import kotlinx.android.synthetic.main.pop_cancel_confirm.*
import kotlinx.android.synthetic.main.pop_cancel_confirm.view.*

class ManageFragment() : Fragment() {

    lateinit var db: UsersDBHelper
    lateinit var exview : View
    lateinit var userAdapter : UserAdapter

    private var edit_mode = false
    private val buffer = ArrayList<UserModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        db = UsersDBHelper(requireContext())
        return inflater.inflate(R.layout.fragment_manage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        exview = view
        userAdapter = initRecycleView(view)
        Edit_state(view)

        NavBar().setGo(3, view)
    }

    private fun initRecycleView(view:View):UserAdapter{
        Toast.makeText(activity," user :: ${db.getAllCustom()}",Toast.LENGTH_LONG).show()
        val Adapter_ =  UserAdapter(requireActivity())
        Adapter_.insertItem(db.getAllCustom())
        view.userListView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = Adapter_
        }
        return Adapter_
    }

    private fun discardChange(view :View) {
        val popView = LayoutInflater.from(requireContext()).inflate(R.layout.discard_change, null)
        val dialog = AlertDialog.Builder(requireContext())
            .setView(popView)
            .create()
        dialog.show()

        popView.run{
            No.setOnClickListener { dialog.dismiss() }
            Yes.setOnClickListener {
                disChange(view)
                dialog.dismiss()
            }
        }
    }

    private fun error(e:Exception){
        Toast.makeText(requireContext(),"Error $e",Toast.LENGTH_LONG).show()
    }

    private fun disChange(view : View){
        userAdapter.run{
            this.edit_mode = false
            restoreOldList()
            notifyDataSetChanged()
        }

        buffer.clear()
        End_Anime(view)
    }

    private fun Edit_state(view:View){

        view.Edit.setOnClickListener {
            Start_Anime(view)
            userAdapter.run{
                edit_mode = true
                backupOldList()
                notifyDataSetChanged()
            }
            buffer.clear()
        }

        view.Cancel.setOnClickListener{
            discardChange(view)
        }

        view.Done.setOnClickListener{
            db.run {
                deleteAllUser(userAdapter.getDelete())
                addAllUser(buffer)
            }

            userAdapter.run{
                this.edit_mode  = false
                insertItem(db.getAllCustom())
            }
            End_Anime(view)
            buffer.clear()
        }

        view.Add.setOnClickListener {
            val addUserDialog = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_mange_addcontact,null)
            val addBuilder = AlertDialog.Builder(requireContext())
                .setView(addUserDialog)
                .create()

            addBuilder.show()
            addContactData(addUserDialog,addBuilder)
        }
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
                val exitDialog = LayoutInflater.from(requireContext()).inflate(R.layout.pop_cancel_confirm,null)
                val exitBuild = AlertDialog.Builder(requireContext())
                    .setView(exitDialog)
                    .create()

                exitBuild.show()

                exitDialog.Yes.setOnClickListener{
                    exitBuild.dismiss()
                    builder.dismiss()
                }

                exitDialog.No.setOnClickListener{
                    exitBuild.dismiss()
                }
            }

            submitWith(view.btn_Add){
                try{
                    val user = UserModel(
                            0,
                            view.Name_.text.toString(),
                            view.Phone_.text.toString(),
                            view.Relationship_.text.toString(),
                            true)
                    buffer.add(user)
                    userAdapter.addItem(user)
                    Log.d("adapter","buffer check -> $buffer")
                    builder.dismiss()
                }catch (e : Exception){error(e) }
            }
        }
    }

    private fun Start_Anime(view:View){
        val fade = AnimationUtils.loadAnimation(requireContext(), R.anim.fade)
        val sd = AnimationUtils.loadAnimation(requireContext(),R.anim.slide_down)
        val sd_add = AnimationUtils.loadAnimation(requireContext(),R.anim.fade_in)

        view.run{
            block.startAnimation(sd)
            block.translationY = 30F

            Add.startAnimation(sd_add)
            Done.startAnimation(sd_add)
            Cancel.startAnimation(sd_add)

            Edit.startAnimation(fade)

            Cancel.visibility =  View.VISIBLE
            Done.visibility =  View.VISIBLE
            Add.visibility = View.VISIBLE

            Edit.visibility = View.INVISIBLE
        }


    }

    private fun End_Anime(view: View){
        val fade = AnimationUtils.loadAnimation(activity, R.anim.fade_in)
        val sd = AnimationUtils.loadAnimation(activity, R.anim.slide_up)
        val sd_add = AnimationUtils.loadAnimation(activity,R.anim.fade)
        view.run{
            block.translationY = 0F
            block.startAnimation(sd)

            Add.startAnimation(sd_add)
            Cancel.startAnimation(sd_add)
            Done.startAnimation(sd_add)

            Edit.visibility = View.VISIBLE
            Cancel.visibility = View.INVISIBLE
            Done.visibility = View.INVISIBLE
            Add.visibility = View.GONE
        }
    }

}
