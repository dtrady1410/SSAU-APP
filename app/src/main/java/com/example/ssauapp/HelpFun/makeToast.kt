package com.example.ssauapp.HelpFun

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.makeToast(message: String){
    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show()
}