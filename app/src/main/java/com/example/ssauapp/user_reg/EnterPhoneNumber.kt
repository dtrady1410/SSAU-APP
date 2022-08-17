package com.example.ssauapp.user_reg

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.ssauapp.HelpFun.makeToast
import com.example.ssauapp.MainActivity
import com.example.ssauapp.R
import com.example.ssauapp.RegisterActivity
import com.example.ssauapp.databinding.FragmentEnterPhoneNumberBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class EnterPhoneNumber : Fragment(R.layout.fragment_enter_phone_number) {
    lateinit var binding: FragmentEnterPhoneNumberBinding
    private lateinit var mPhoneNumber: String
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    lateinit var fragmnetManager: FragmentManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterPhoneNumberBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = EnterPhoneNumber()
    }

    override fun onStart() {
        super.onStart()
        val intnet = Intent(activity as RegisterActivity, MainActivity::class.java)
        mAuth = FirebaseAuth.getInstance()
        mCallBack = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                mAuth.signInWithCredential(credential).addOnCompleteListener{
                    if(it.isSuccessful){
                        makeToast(getString(R.string.welcome_message))
                        (activity as RegisterActivity).finish()
                        startActivity(intnet)
                    } else{
                        makeToast(it?.exception?.message.toString())
                    }
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(activity, p0.message.toString(), Toast.LENGTH_SHORT ).show()
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                fragmnetManager = (activity as RegisterActivity).supportFragmentManager
                fragmnetManager
                    .beginTransaction()
                    .replace(R.id.vp_reg, EnterCodeFragmnet(id, mPhoneNumber)).commit()
            }
        }
        binding.btnNext.setOnClickListener{ checkFunction() }
    }

    private fun checkFunction() {
        if(binding.editPhone.text.toString().isEmpty()){
            makeToast(getString(R.string.toast_phone_enter))
        } else{
            authUser()
        }
    }

    private fun authUser() {
        mPhoneNumber = binding.editPhone.text.toString()
        val options: PhoneAuthOptions = PhoneAuthOptions
            .newBuilder(mAuth)
            .setPhoneNumber(mPhoneNumber)
            .setTimeout(60, TimeUnit.SECONDS)
            .setActivity(activity as RegisterActivity)
            .setCallbacks(mCallBack)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

}