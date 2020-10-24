package com.example.suppliersadda

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

open class LoginActivity : AppCompatActivity() {


    lateinit var TextEmailId:TextInputEditText
    lateinit var TextPasswordId:TextInputEditText
    lateinit var LoginButton:Button
    lateinit var RessisterTv:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        TextEmailId=findViewById(R.id.emailId_et_login)
        TextPasswordId=findViewById(R.id.pass_et_login)
        LoginButton=findViewById(R.id.login_button)
        RessisterTv=findViewById(R.id.resister_tv_login)


    }


    fun ButtonClick(view: View) {
        val loginModel=LoginModel()
        when(view){
            LoginButton->{ loginModel.LoginTextCheck(TextEmailId,TextEmailId)
                val email=emailId_et_login.text.toString()
                val password=pass_et_login.text.toString()
                Log.d("LoginActivity","login email :$email")
                Log.d("LoginActivity","login password :$password")
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                  .addOnCompleteListener {
                      val intent=Intent(this,MainActivity::class.java)
                      startActivity(intent)
                  }
                  .addOnFailureListener {  }
            }
            RessisterTv->{
                val intent=Intent(this,Registration::class.java)
                startActivity(intent)
            }
        }
    }
}