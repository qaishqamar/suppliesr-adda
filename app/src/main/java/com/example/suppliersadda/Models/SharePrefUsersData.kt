package com.example.suppliersadda.Models

import android.app.Activity
import android.content.Context
import android.util.Log
import com.example.suppliersadda.Activity.Registration
import com.google.firebase.auth.FirebaseAuth

class SharePrefUsersData(val activity: Activity) {
   // lateinit var userPref:SharedPreferences



   val userPref=activity.getSharedPreferences(Registration.FileNameSherdPref, Context.MODE_PRIVATE)

    fun getUserDataPref(): UserData {

        val uid= FirebaseAuth.getInstance().uid?:""
        var userPic:String?=""
        var  userName:String?=""
        var userNum:String?=""
        var  userEmail:String?=""
        Log.d("Main", "data GET PROCESS")
        if (userPref.contains("USER_PIC")&&userPref.contains("USER_NAME")&&userPref.contains("USER_NUMB")&&userPref.contains("USER_EMAIL")){

            userPic=   userPref.getString("USER_PIC","")
            userName=userPref.getString("USER_NAME","")
            userNum=userPref.getString("USER_NUMB","")
            userEmail= userPref.getString("USER_EMAIL","")

        }
        Log.d("Main", "data GET PROCESS 6")
        val userShare= UserData(uid,userName!!,userNum!!,userEmail!!,userPic!!)
        return userShare
    }

    fun saveUserData(userNameRg:String,mobileNoRg:String,emailRg:String,resultUri:String){
        val uid=FirebaseAuth.getInstance().uid?:""

        val user= UserData(
            uid,
            userNameRg,
            mobileNoRg,
            emailRg,
            resultUri
        )

        if(uid!=null){
            val userPrefEditor= userPref.edit()
            userPrefEditor.putString("USER_PIC",user.image)
            userPrefEditor.putString("USER_NAME",user.userNmae)
            userPrefEditor.putString("USER_NUMB",user.PhoneNo)
            userPrefEditor.putString("USER_EMAIL",user.email)
            userPrefEditor.commit()
            Log.d("Main", "data saved succesfully")
        }


    }
}