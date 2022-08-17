package com.example.ssauapp.Fragments.NavFrag

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.ssauapp.Fragments.NavFrag.HelpFragments.ProfileEditFragment
import com.example.ssauapp.MainActivity
import com.example.ssauapp.R
import com.example.ssauapp.RegisterActivity
import com.example.ssauapp.Utilits.AUTH
import com.example.ssauapp.Utilits.User
import com.example.ssauapp.databinding.FragmentEditUserParamsRegBinding
import com.example.ssauapp.databinding.FragmentUserRoomBinding
import com.example.ssauapp.user_reg.Edit_User_Params_Reg_Fragment
import com.google.firebase.auth.FirebaseAuth

class FragmentUser_Room : Fragment() {
    lateinit var binding: FragmentUserRoomBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = FragmentUser_Room()
    }

    override fun onStart() {
        AUTH = FirebaseAuth.getInstance()
        super.onStart()
        binding.logouttBtn.setOnClickListener{
            exitFun()
        }
        getParams()
        binding.editProfileBtn.setOnClickListener{
            editProfile()
        }
    }

    private fun editProfile() {
        (activity as MainActivity).supportFragmentManager
            .beginTransaction()
            .replace(R.id.vp, ProfileEditFragment())
            .addToBackStack("editProfile")
            .commit()
    }

    fun exitFun() {
        val intent = Intent(activity as MainActivity, RegisterActivity::class.java)
        (activity as MainActivity).finish()
        startActivity(intent)
         AUTH.signOut()
     }

    private fun getParams() = with(binding){
        val nameList = User.name.split(" ")
        tvProfileName.text = User.name
        tvProfileFirstName.text = nameList[0]
        tvProfileSurname.text = nameList[1]
    }

}