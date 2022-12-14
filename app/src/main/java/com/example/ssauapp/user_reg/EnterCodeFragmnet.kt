package com.example.ssauapp.user_reg

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.ssauapp.HelpFun.makeToast
import com.example.ssauapp.MainActivity
import com.example.ssauapp.R
import com.example.ssauapp.RegisterActivity
import com.example.ssauapp.Utilits.*
import com.example.ssauapp.databinding.FragmentEnterCodeFragmnetBinding
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragmnet(val id: String, val mPhoneNumber: String) : Fragment(R.layout.fragment_enter_code_fragmnet) {
    lateinit var binding: FragmentEnterCodeFragmnetBinding
    lateinit var fragmnetManager: FragmentManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterCodeFragmnetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        binding.editCode.addTextChangedListener(object :TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                val string = binding.editCode.text.toString()
                if (string.length == 6){
                    enterCode()
                }
            }
        })
    }

    private fun enterCode() {
        val code = binding.editCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        val intent = Intent(activity as RegisterActivity, MainActivity::class.java)
        AUTH.signInWithCredential(credential)
            .addOnCompleteListener{task ->
            if(task.isSuccessful){
                val uid = AUTH.currentUser?.uid.toString()
                val date_map = mutableMapOf<String, Any>()
                date_map[CHILD_ID] = uid
                date_map[CHILD_PHONE] = mPhoneNumber
                FIREBASE_REALTIME_DATABASE.child(NODE_USERS).child(uid).updateChildren(date_map)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            (activity as RegisterActivity).finish()
                            startActivity(intent)
                            makeToast(getString(R.string.toast_enter_code_successful))
                        } else {
                            makeToast(task.exception.toString())
                        }
                    }
            }else {
                Toast.makeText(activity, task?.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}