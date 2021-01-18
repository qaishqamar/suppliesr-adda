package com.example.suppliersadda.Activity

//import com.google.firebase.storage.FirebaseStorage
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.suppliersadda.Models.LoginModel
import com.example.suppliersadda.Models.SharePrefUsersData
import com.example.suppliersadda.Models.UserData
import com.example.suppliersadda.PermissionGet
import com.example.suppliersadda.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import java.util.*


class Registration : AppCompatActivity() {

    companion object{
        var permissionGrant=false
        val PermissionCode=1
        val FileNameSherdPref="com.supplierAdda.UserProfile"
        val vehicleArray= arrayOf("CARS", "TRUCKS", "TRACTOR", "JCB", "AUTO", "SUV", "BUS")
        val  vehiclePicArray= arrayOf(


            R.drawable.car,
            R.drawable.vehicle_pic_icon ,
            R.drawable.vehicle_pic_icon,
            R.drawable.vehicle_pic_icon,
            R.drawable.vehicle_pic_icon,
            R.drawable.vehicle_pic_icon,
            R.drawable.vehicle_pic_icon
        )
        val localitiesArray = arrayOf(
            "BOOTY MORE",
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
            "Gandhinagar"
        )
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
    lateinit var userPref:SharedPreferences

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
        userPref=getSharedPreferences(FileNameSherdPref,Context.MODE_PRIVATE)

        registerBtRg.setOnClickListener {
            val loginModel= LoginModel()

            val strUserName=userNameRg.text.toString()
            val textcheckRes=loginModel.LoginTextCheck(emailRg, passwordRg)
            if (!TextUtils.isEmpty(strUserName)&&textcheckRes){
                strEmail=emailRg.text.toString()
                strPassword=passwordRg.text.toString()
             authentication()
                }
        }
        imageTakRg.setOnClickListener {
            val permissionGet= PermissionGet(this, this@Registration)
            val permissionName=android.Manifest.permission.READ_EXTERNAL_STORAGE
            if (permissionGet.checkperrmission(permissionName)){
                Log.d("Main", "Try to show photo selecter")
//                val intent=Intent(Intent.ACTION_PICK)
//                intent.type="image/*"
//                startActivityForResult(intent,0)
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .start(this);

            }

            }

    }

   var  resultUri:Uri?=null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            //proceed and check what thee image is selected

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                Log.d("Main", "image is selected")
                val result = CropImage.getActivityResult(data)
                if (resultCode == RESULT_OK) {
                     resultUri = result.uri

                    imageTakRg.setImageURI(resultUri)
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    val error = result.error
                }
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
                    permissionGrant = true
                    Log.d("phone no", "Contact no:- trying to reed contacts")
                } else {
                    Toast.makeText(this, "permission Denied", Toast.LENGTH_SHORT).show()
                    permissionGrant = false
                }
            }

        }
    }

    val Rtag="ressiter"
    fun authentication(){

        mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
            .addOnCompleteListener {
                if (!it.isSuccessful) {
                    return@addOnCompleteListener
                    Log.d(Rtag, " successfuly created acount of uid ${it.result}")}
                //else successful
                else{
                   // uploadImagrToFirebase()
                    val sharePrefUsersData= SharePrefUsersData(this)
                    sharePrefUsersData.saveUserData(userNameRg.text.toString(),
                        mobileNoRg.text.toString(),
                        emailRg.text.toString(),
                        resultUri.toString())
                    Log.d(Rtag, " successfuly created acount of uid ${it.result!!.user.uid}")
                }



            }
            .addOnFailureListener {  Log.d(Rtag, " Failed ${it.message}") }
    }
//    fun saveUserData(){
//        val uid=mAuth.uid?:""
//        userPref=getSharedPreferences(FileNameSherdPref,Context.MODE_PRIVATE)
//       val user= UserData(
//            uid,
//            userNameRg.text.toString(),
//            mobileNoRg.text.toString(),
//            emailRg.text.toString(),
//            resultUri.toString()
//        )
//
//        if(uid!=null){
//            val userPrefEditor= userPref.edit()
//            userPrefEditor.putString("USER_PIC",user.image)
//            userPrefEditor.putString("USER_NAME",user.userNmae)
//            userPrefEditor.putString("USER_NUMB",user.PhoneNo)
//            userPrefEditor.putString("USER_EMAIL",user.email)
//            userPrefEditor.commit()
//            Log.d("Main", "data saved succesfully")
//            }
//
//
//    }
//    fun getUserDataPref(activity: Activity):UserData{
//        userPref=activity.getSharedPreferences(FileNameSherdPref,Context.MODE_PRIVATE)
//        val uid=FirebaseAuth.getInstance().uid?:""
//        var userPic:String?=""
//        var  userName:String?=""
//        var userNum:String?=""
//        var  userEmail:String?=""
//        Log.d("Main", "data GET PROCESS")
//       if (userPref.contains("USER_PIC")&&userPref.contains("USER_NAME")&&userPref.contains("USER_NUMB")&&userPref.contains("USER_EMAIL")){
//           Log.d("Main", "data GET PROCESS 2")
//         userPic=   userPref.getString("USER_PIC","")
//           Log.d("Main", "data GET PROCESS 3")
//           userName=userPref.getString("USER_NAME","")
//           Log.d("Main", "data GET PROCESS 4")
//            userNum=userPref.getString("USER_NUMB","")
//           Log.d("Main", "data GET PROCESS 5")
//          userEmail= userPref.getString("USER_NAME","")
//
//       }
//        Log.d("Main", "data GET PROCESS 6")
//        val userShare=UserData(uid,userName!!,userNum!!,userEmail!!,userPic!!)
//        return userShare
//    }
    private fun uploadImagrToFirebase(){
        val filename= UUID.randomUUID().toString()
        val ref= FirebaseStorage.getInstance().getReference("/image/$filename")
        ref.putFile(selected_photo_uri!!)
            .addOnSuccessListener {
                Log.d("Main", "image is uploaded sucessfully ${it.metadata?.path}")
                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Main", "File location :$it")

                    saveUserToFirebase(it.toString())
                }
            }
            .addOnFailureListener{
                Toast.makeText(this, "Image url is saving Failed", Toast.LENGTH_SHORT).show()
            }

    }
    private fun saveUserToFirebase(profileImageUrl: String){
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
                Log.d("Main", "user detail is uploaded")
                val intent=Intent(this, MainActivity::class.java)
                intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }

            .addOnFailureListener {
                Log.d("Main", "details re not uploaded :try again")
                Toast.makeText(this, "users detail not uploaded", Toast.LENGTH_SHORT).show()

            }
    }
}
