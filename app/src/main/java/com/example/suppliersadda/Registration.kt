package com.example.suppliersadda

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.FirebaseStorage
import java.util.*


class Registration : AppCompatActivity() {

    companion object{
        var permissionGrant=false
        val PermissionCode=1
        val localitiesArray = arrayOf("BOOTY MORE",
            "IRBA",
            "Chutia",
            "BARIATU",
            "BARAGAIN",
            "Kadru",
            "KANKE",
            "PUNDAG",
            "Argora",
            "BIT MORE",
            "HINDPIDHI",
            "Kutcherry",
            "BIRSA CHOK",
            "Harmu",
            "LALPUR",
            "SUKURHUTTU",
            "Hatma",
            "KANTATOLY",
            "BOREYA",
            "Hinoo",
            "ORMANJHI CHOK",
            "HEHEL",
            "Ranibagan",
            "BAHUBAZAR",
            "SIMLIA",
            "PISKA MORE",
            "KAMRE",
            "Puruliya",
            "DHURWA",
            "PANDRA",
            "Ambatoli",
            "DORANDA",
            "ITKI",
            "MANDER ",
            "GUTUA",
            "PUNDAG",
            "Morabadi",
            "KHELGAON(TATISILWE)",
            "SIDROL",
            "CHANHO",
            "KHJURTOLA",
            "NAMKUM",
            "NAYASARAY",
            "Samlong",
            "Patratoly",
            "Jagannathpur",
            "Kanandu",
            "Hatia",
            "Pithoria",
            "Chandwe",
            "Pithoria",
            "Kamta",
            "Jaipur",
            "Husir",
            "Neori",
            "Gandhinagar")
    }
    lateinit var emailRg:EditText
    lateinit var passwordRg:EditText
    lateinit var userNameRg:EditText
    lateinit var mobileNoRg:EditText
    lateinit var registerBtRg:Button
    lateinit var imageTakRg:ImageView
    var selected_photo_uri: Uri? =null
    lateinit var mAuth: FirebaseAuth
    lateinit var strEmail:String
    lateinit var strPassword:String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        emailRg=findViewById(R.id.emailId_et_Rg)
        passwordRg=findViewById(R.id.pass_et_Rg)
        userNameRg=findViewById(R.id.userName_reg_et)
        mobileNoRg=findViewById(R.id.mobileNo_reg_et)
        registerBtRg=findViewById(R.id.register_button)
        imageTakRg=findViewById<ImageView>(R.id.imageAddIv)

        //for authentication
        mAuth = FirebaseAuth.getInstance()


        registerBtRg.setOnClickListener {
            val loginModel=LoginModel()

            val strUserName=userNameRg.text.toString()
            val textcheckRes=loginModel.LoginTextCheck(emailRg,passwordRg)
            if (!TextUtils.isEmpty(strUserName)&&textcheckRes){
                strEmail=emailRg.text.toString()
                strPassword=passwordRg.text.toString()
             authentication()
                }
        }
        imageTakRg.setOnClickListener {
            val permissionGet=PermissionGet(this,this@Registration)
            val permissionName=android.Manifest.permission.READ_EXTERNAL_STORAGE
            if (permissionGet.checkperrmission(permissionName)){
                Log.d("Main","Try to show photo selecter")
                val intent=Intent(Intent.ACTION_PICK)
                intent.type="image/*"
                startActivityForResult(intent,0)}
            }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==0&&resultCode== Activity.RESULT_OK&&data!=null){
            //proceed and check what thee image is selected
            Log.d("Main","image is selected")
            selected_photo_uri=data.data
            val bitmap= MediaStore.Images.Media.getBitmap(contentResolver,selected_photo_uri)
            imageTakRg.setImageBitmap(bitmap)
//            imageTakRg.alpha=0f
            //   val bitmapDrawable=BitmapDrawable(bitmap)
            //   SelectImage_button_register.setBackgroundDrawable(bitmapDrawable)
        }
    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermissionCode -> {
                if (grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show()
                   permissionGrant=true
                    Log.d("phone no", "Contact no:- trying to reed contacts")
                }
                else {
                    Toast.makeText(this, "permission Denied", Toast.LENGTH_SHORT).show()
                    permissionGrant=false
                }
            }

        }
    }

    val Rtag="ressiter"
    fun authentication(){

        mAuth.createUserWithEmailAndPassword(strEmail,strPassword)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                    Log.d(Rtag," successfuly created acount of uid ${it.result}")}
                //else successful
                else{
                    uploadImagrToFirebase()
                    Log.d(Rtag," successfuly created acount of uid ${it.result!!.user.uid}")
                }



            }
            .addOnFailureListener {  Log.d(Rtag," Failed ${it.message}") }
    }
    private fun uploadImagrToFirebase(){
        val filename= UUID.randomUUID().toString()
        val ref= FirebaseStorage.getInstance().getReference("/image/$filename")
        ref.putFile(selected_photo_uri!!)
            .addOnSuccessListener {
                Log.d("Main","image is uploaded sucessfully ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Main","File location :$it")

                    saveUserToFirebase(it.toString())
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,"Image url is saving Failed",Toast.LENGTH_SHORT).show()
            }

    }
    private fun saveUserToFirebase(profileImageUrl:String){
        val uid=mAuth.uid?:""
        val user= UserData(
            uid,
            userNameRg.text.toString(),
            mobileNoRg.text.toString(),
            emailRg.text.toString(),
            profileImageUrl
        )
        val ref= FirebaseDatabase.getInstance().getReference("/usersData/$uid")

        ref.setValue(user)
            .addOnSuccessListener {
                Log.d("Main","user detail is uploaded")
                val intent=Intent(this, MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            .addOnFailureListener {
                Log.d("Main","details re not uploaded :try again")
                Toast.makeText(this,"users detail not uploaded",Toast.LENGTH_SHORT).show()

            }
    }
}
