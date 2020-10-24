package com.example.suppliersadda

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_shell.*
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//private const val ARG_PARAM1 = "param1"
//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShellFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShellFragment : Fragment() {
    lateinit var localityAutoCompleteTextView: AutoCompleteTextView
    lateinit var DealerResisterbtn: Button
    lateinit var pic1Btn: Button
    lateinit var pic2Btn: Button
    lateinit var pic3Btn: Button
    lateinit var pic1Img: ImageView
    lateinit var pic2Img: ImageView
    lateinit var pic3Img: ImageView
    lateinit var vehicle: EditText
    var selected_photo1_uri: Uri? = null
    var selected_photo2_uri: Uri? = null
    var selected_photo3_uri: Uri? = null


    lateinit var mContext: Context
    lateinit var mAuth: FirebaseAuth

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext=context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        return inflater.inflate(R.layout.fragment_shell, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //
        // variable initialisation

        localityAutoCompleteTextView = view!!.findViewById(R.id.localityId)
        DealerResisterbtn = view!!.findViewById(R.id.DealerResisterButton)
        pic1Btn = view!!.findViewById(R.id.SelectImage1_button_register)
        pic2Btn = view!!.findViewById(R.id.SelectImage2_button_register)
        pic3Btn = view!!.findViewById(R.id.SelectImage3_button_register)
        pic1Img = view!!.findViewById(R.id.select_circleImageView1_register)
        pic2Img = view!!.findViewById(R.id.select_circleImageView2_register)
        pic3Img = view!!.findViewById(R.id.select_circleImageView3_register)



        val arrayAdapter =
            ArrayAdapter(activity!!, android.R.layout.simple_list_item_1, Registration.localitiesArray)
        localityId.setAdapter(arrayAdapter)
        val permissionGet=PermissionGet(context!!,activity!!)

        DealerResisterbtn.setOnClickListener {
            Toast.makeText(context, "button clicked", Toast.LENGTH_SHORT).show()

        }
        pic1Btn.setOnClickListener {

            val permissionName=Manifest.permission.READ_EXTERNAL_STORAGE
            if (permissionGet.checkperrmission(permissionName)){
                Log.e("DB", "PERMISSION GRANTED")
                PickImage(1)
            }

        }

        pic2Btn.setOnClickListener {
            val permissionName=Manifest.permission.READ_EXTERNAL_STORAGE
          if (permissionGet.checkperrmission(permissionName)){
              Log.e("DB", "PERMISSION GRANTED")
              PickImage(2)
          }
        }
        pic3Btn.setOnClickListener {
            val permissionName=Manifest.permission.READ_EXTERNAL_STORAGE
            if (permissionGet.checkperrmission(permissionName)){
                Log.e("DB", "PERMISSION GRANTED")
                PickImage(3)
            }
        }
       DealerResisterbtn.setOnClickListener {
           if (vehicle.text.toString()!=null&&localityAutoCompleteTextView.text.toString()!=null&&pinCodeID.text.toString()!=null&&cityAdresEtShel.text.toString()!=null){

           }
       }

    }
     fun PickImage(code: Int){
         Log.d("Main", "Try to show photo selecter")
         val intent = Intent(Intent.ACTION_PICK)
         intent.type = "image/*"
         startActivityForResult(intent, code)
     }
/*
for permission override
*/
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Registration.PermissionCode -> {
                if (grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show()
                    Registration.permissionGrant =true
                    Log.d("phone no", "Contact no:- trying to reed contacts")
                }
                else {
                    Registration.permissionGrant =false
                    Toast.makeText(context,"Permission denied in fragment ",Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode> 0&&requestCode<=3 && resultCode == Activity.RESULT_OK && data != null) {
            //proceed and check what thee image is selected
            Log.d("Main", "image is selected")

            when (requestCode) {
                1 -> {
                    selected_photo1_uri = data.data
                    pic1Img.setImageURI(selected_photo1_uri)
                    pic1Btn.alpha = 0f
                }
                2 -> {
                    selected_photo2_uri = data.data
                    pic2Img.setImageURI(selected_photo2_uri)
                    pic2Btn.alpha = 0f
                }
                3 -> {
                    selected_photo3_uri = data.data
                    pic3Img.setImageURI(selected_photo3_uri)
                    pic3Btn.alpha = 0f
                }

            }

        }


    }
//    private fun uploadImagrToFirebase(){
//        val filename= UUID.randomUUID().toString()
//        val ref= FirebaseStorage.getInstance().getReference("/image/$filename")
//        ref.putFile(selected_photo_uri!!)
//            .addOnSuccessListener {
//                Log.d("Main","image is uploaded sucessfully ${it.metadata?.path}")
//                ref.downloadUrl.addOnSuccessListener {
//                    Log.d("Main","File location :$it")
//
//                    saveUserToFirebase(it.toString())
//                }
//            }
//            .addOnFailureListener{
//                Toast.makeText(this,"Image url is saving Failed",Toast.LENGTH_SHORT).show()
//            }


}


