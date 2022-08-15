package com.example.ssauapp.user_reg

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.ssauapp.MainActivity
import com.example.ssauapp.R
import com.example.ssauapp.RegisterActivity
import com.example.ssauapp.Utilits.*
import com.example.ssauapp.databinding.FragmentEditUserParamsRegBinding
import com.example.ssauapp.databinding.FragmentUserRoomBinding
import com.google.firebase.auth.PhoneAuthProvider

class Edit_User_Params_Reg_Fragment(val id: String, val mPhoneNumber: String) : Fragment() {
    lateinit var binding: FragmentEditUserParamsRegBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditUserParamsRegBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.CreateAccountBtn.setOnClickListener{
            create_account()
        }
    }

    private fun create_account() {
        val uid = AUTH.currentUser?.uid.toString()
        val date_map = mutableMapOf<String, Any>()
        val intent = Intent(activity as RegisterActivity, MainActivity::class.java)
        if (!(binding.FirstNameEdit.text.isEmpty() && binding.LastNameEdit.text.isEmpty())) {
            date_map[CHILD_ID] = uid
            date_map[CHILD_PHONE] = mPhoneNumber
            date_map[CHIlD_FIRSTNAME] = binding.FirstNameEdit.text.toString()
            date_map[CHILD_LASTNAME] = binding.LastNameEdit.text.toString()
            FIREBASE_REALTIME_DATABASE.child(NODE_USERS).child(uid).updateChildren(date_map)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        (activity as RegisterActivity).finish()
                        startActivity(intent)
                        makeToast(
                            activity as RegisterActivity,
                            getString(R.string.welcome_message),
                            Toast.LENGTH_SHORT
                        )
                    } else {
                        makeToast(
                            activity as RegisterActivity,
                            task.exception.toString(),
                            Toast.LENGTH_SHORT
                        )
                    }
                }
        }else{
            makeToast(activity as RegisterActivity,
                getString(R.string.UserInfoErrorToast),
                Toast.LENGTH_SHORT)
        }
    }
}