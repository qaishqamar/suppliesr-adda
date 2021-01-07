package com.example.suppliersadda.Models

import android.text.TextUtils
import android.widget.EditText
import com.example.suppliersadda.Activity.LoginActivity
import java.util.regex.Matcher
import java.util.regex.Pattern

class LoginModel(): LoginActivity(){
     fun LoginTextCheck(email:EditText,pass:EditText):Boolean{
        val strEmail=email.text.toString().trim()
        val strPassword= pass.text.toString().trim()
        if (TextUtils.isEmpty(strEmail)){
            email.error="Email is empty"
            return false
        }else if(TextUtils.isEmpty(strPassword)||strPassword.trim().length<6){
            pass.error=" password must be atleast in 6 charactors"
            return false
        }
        else if (!emailVlidation(strEmail)){
            email.error="Email is not valid"
            return false
        }else{
          return true
        }


    }
    fun emailVlidation(strEmail:String):Boolean{
        val EMAIL_PATTERN="^[_A-Za-z0-9-]+(\\.[_A-za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        val pattern=Pattern.compile(EMAIL_PATTERN)
        val matcher:Matcher=pattern.matcher(strEmail)
        return matcher.matches()
    }
}