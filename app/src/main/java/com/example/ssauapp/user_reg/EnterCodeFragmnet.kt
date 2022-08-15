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
import com.example.ssauapp.MainActivity
import com.example.ssauapp.R
import com.example.ssauapp.RegisterActivity
import com.example.ssauapp.Utilits.AUTH
import com.example.ssauapp.Utilits.makeToast
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
                val user = task.result.user
                val creationTimestamp = user?.metadata?.creationTimestamp
                val lastSignInTimestamp= user?.metadata?.lastSignInTimestamp
                if (creationTimestamp==lastSignInTimestamp){
                    makeToast(activity as RegisterActivity, getString(R.string.toast_enter_code_successful), Toast.LENGTH_SHORT)
                    fragmnetManager = (activity as RegisterActivity).supportFragmentManager
                    fragmnetManager
                        .beginTransaction()
                        .replace(R.id.vp_reg, Edit_User_Params_Reg_Fragment(id, mPhoneNumber))
                        .commit()
                }else{
                    (activity as RegisterActivity).finish()
                    startActivity(intent)
                }
            }else{
                Toast.makeText(activity, task?.exception.toString(), Toast.LENGTH_SHORT).show()
            }
        }
    }

}