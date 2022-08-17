package com.example.ssauapp.user_reg

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.ssauapp.Fragments.NavFrag.FragmentLenta
import com.example.ssauapp.HelpFun.makeToast
import com.example.ssauapp.MainActivity
import com.example.ssauapp.R
import com.example.ssauapp.Utilits.*
import com.example.ssauapp.databinding.FragmentRoleCheckBinding

class RoleCheckFragment : Fragment(R.layout.fragment_role_check) {
    lateinit var binding: FragmentRoleCheckBinding
    lateinit var fragmnetManager: FragmentManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRoleCheckBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() = with(binding){
        super.onStart()
        checkOrg.setOnClickListener{
            if(checkOrg.isChecked) {FIREBASE_EDIT(checkOrg.text.toString())}
        }
        checkStudent.setOnClickListener{
            if(checkStudent.isChecked){FIREBASE_EDIT(checkStudent.text.toString())}
        }
    }

    private fun FIREBASE_EDIT(role: String){
        fragmnetManager = (activity as MainActivity).supportFragmentManager
        FIREBASE_REALTIME_DATABASE.child(NODE_USERS).child(UID).child(CHILD_ROLE)
            .setValue(role).addOnCompleteListener {student ->
                if(student.isSuccessful){
                    makeToast(getString(R.string.toast_enter_code_successful))
                    User.role = role
                    fragmnetManager.beginTransaction()
                        .replace(R.id.vp, FragmentLenta())
                        .commit()
                    (activity as MainActivity).binding.toolbar.visibility = View.VISIBLE
                    (activity as MainActivity).binding.navBottom.visibility = View.VISIBLE
                }
                else{
                    makeToast(student.exception.toString())
                }
        }
    }
}