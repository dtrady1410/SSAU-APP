package com.example.ssauapp.Fragments.NavFrag.HelpFragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.example.ssauapp.Fragments.NavFrag.FragmentLenta
import com.example.ssauapp.MainActivity
import com.example.ssauapp.R
import com.example.ssauapp.Utilits.*
import com.example.ssauapp.databinding.FragmentProfileEditBinding

class ProfileEditFragment : Fragment() {
    lateinit var binding: FragmentProfileEditBinding
    lateinit var fragmnetManager: FragmentManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).binding.toolbar. visibility = View.GONE
        (activity as MainActivity).binding.navBottom.visibility = View.GONE
        binding.CreateAccountBtnProfile.setOnClickListener{
            editUserInfo()
        }
        val nameList = User.name.split(" ")
        binding.FirstNameEditProfile.setText(nameList[0])
        binding.LastNameEditProfile.setText(nameList[1])
    }

    private fun editUserInfo() = with(binding){
        fragmnetManager = (activity as MainActivity).supportFragmentManager
        val name = FirstNameEditProfile.text.toString()
        val surname = LastNameEditProfile.text.toString()
        if(name.isEmpty() || surname.isEmpty()){
            makeToast(activity as MainActivity,
                getString(R.string.Edit_User_Params_Tost_Input_Error),
                Toast.LENGTH_SHORT)
        }else{
            val fullname = "$name $surname"
            FIREBASE_REALTIME_DATABASE.child(NODE_USERS).child(UID).child(CHIlD_FULLNAME)
                .setValue(fullname).addOnCompleteListener {it ->
                    if(it.isSuccessful){
                        makeToast(activity as MainActivity,
                            getString(R.string.toast_enter_code_successful),
                            Toast.LENGTH_SHORT)
                        User.name = fullname
                        (activity as MainActivity).binding.toolbar. visibility = View.VISIBLE
                        (activity as MainActivity).binding.navBottom.visibility = View.VISIBLE
                        fragmnetManager.popBackStack()
                    }else{
                        makeToast(activity as MainActivity, it.exception.toString(),
                            Toast.LENGTH_SHORT)
                    }
                }
        }
    }
}