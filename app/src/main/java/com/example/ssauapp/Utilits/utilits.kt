package com.example.ssauapp.Utilits

import android.content.Context
import android.widget.Toast
import com.example.ssauapp.data_class.User_Data
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.security.AccessControlContext

lateinit var AUTH: FirebaseAuth
lateinit var FIREBASE_REALTIME_DATABASE: DatabaseReference

const val NODE_USERS = "users"
const val CHILD_ID = "uid"
const val CHILD_PHONE = "phone"
const val CHIlD_FULLNAME = "name"
const val CHILD_FACULTY = "faculty"
const val CHILD_ROLE = "role"
lateinit var User: User_Data
lateinit var UID: String



fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    FIREBASE_REALTIME_DATABASE = FirebaseDatabase.getInstance("https://ssau-7ad36-default-rtdb.europe-west1.firebasedatabase.app/").reference
    User = User_Data()
    UID = AUTH.currentUser?.uid.toString()
}
