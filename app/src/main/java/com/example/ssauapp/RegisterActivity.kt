package com.example.ssauapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.ssauapp.Utilits.initFirebase
import com.example.ssauapp.databinding.ActivityMainBinding
import com.example.ssauapp.databinding.ActivityRegisterBinding
import com.example.ssauapp.user_reg.EnterCodeFragmnet
import com.example.ssauapp.user_reg.EnterPhoneNumber

class RegisterActivity : AppCompatActivity() {
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        getFragment(R.id.vp_reg, EnterPhoneNumber())
    }

    private fun getFragment(vp: Int, fr: Fragment){
        supportFragmentManager.beginTransaction().replace(vp, fr).commit()
    }

}