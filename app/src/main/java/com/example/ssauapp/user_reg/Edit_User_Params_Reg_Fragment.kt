package com.example.ssauapp.user_reg

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.ssauapp.HelpFun.makeToast
import com.example.ssauapp.MainActivity
import com.example.ssauapp.R
import com.example.ssauapp.Utilits.*
import com.example.ssauapp.databinding.FragmentEditUserParamsRegBinding

class Edit_User_Params_Reg_Fragment() : Fragment() {
    lateinit var binding: FragmentEditUserParamsRegBinding
    lateinit var fragmnetManager: FragmentManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUserParamsRegBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.toolbar. visibility = View.GONE
        (activity as MainActivity).binding.navBottom.visibility = View.GONE
        binding.CreateAccountBtn.setOnClickListener{
            editUserInfo()
        }
    }

    private fun editUserInfo() = with(binding){
        fragmnetManager = (activity as MainActivity).supportFragmentManager
        val name = FirstNameEdit.text.toString()
        val surname = LastNameEdit.text.toString()
        if(name.isEmpty() || surname.isEmpty()){
            makeToast(getString(R.string.Edit_User_Params_Tost_Input_Error))
        }else{
            val fullname = "$name $surname"
            FIREBASE_REALTIME_DATABASE.child(NODE_USERS).child(UID).child(CHIlD_FULLNAME)
                .setValue(fullname).addOnCompleteListener {it ->
                    if(it.isSuccessful){
                        makeToast(getString(R.string.toast_enter_code_successful),)
                        User.name = fullname
                        fragmnetManager.beginTransaction()
                            .replace(R.id.vp, RoleCheckFragment())
                            .commit()
                    }else{
                        makeToast(it.exception.toString())
                    }
                }
        }
    }
}