package com.example.ssauapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.ssauapp.Fragments.NavFrag.FragmentLenta
import com.example.ssauapp.Fragments.NavFrag.FragmentOthers
import com.example.ssauapp.Fragments.NavFrag.FragmentUser_Room
import com.example.ssauapp.Utilits.*
import com.example.ssauapp.data_class.User_Data
import com.example.ssauapp.databinding.ActivityMainBinding
import com.example.ssauapp.user_reg.Edit_User_Params_Reg_Fragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.navBottom.setOnItemSelectedListener {
            it ->
            when(it.itemId){
                R.id.btnMain -> {
                    getFragment(R.id.vp, FragmentLenta.newInstance())
                    binding.toolbar.title = getString(R.string.main_page)

                }
                R.id.btnOthers -> {
                    getFragment(R.id.vp, FragmentOthers.newInstance())
                    binding.toolbar.title = getString(R.string.others)
                }
                R.id.btnUser -> {
                    getFragment(R.id.vp, FragmentUser_Room.newInstance())
                    binding.toolbar.title = getString(R.string.ProfileTitle)
                }
            }
            true
        }
        InitFields()
        InitFunc()
    }

    private fun InitFields() {
        initFirebase()
    }

    private fun InitFunc(){
        if(AUTH.currentUser!=null){
            InitUser()
        }
        else {
            val intent  = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getFragment(vp: Int, fr: Fragment){
        supportFragmentManager.beginTransaction().replace(vp, fr).commit()
    }

    private fun InitUser(){
        FIREBASE_REALTIME_DATABASE.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEvent{
            User = it.getValue(User_Data::class.java) ?: User_Data()
            if(User.name.isEmpty()){
                getFragment(R.id.vp, Edit_User_Params_Reg_Fragment())
            }else{
                getFragment(R.id.vp, FragmentLenta.newInstance())
            }
        })
    }
}