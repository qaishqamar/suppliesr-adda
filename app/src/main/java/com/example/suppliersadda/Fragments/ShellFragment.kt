package com.example.suppliersadda.Fragments

import android.Manifest
import android.app.Activity.RESULT_OK
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
import androidx.fragment.app.Fragment
import com.example.suppliersadda.*
import com.example.suppliersadda.Activity.Registration
import com.example.suppliersadda.Models.DealersDataModel
import com.example.suppliersadda.Models.SharePrefUsersData
import com.example.suppliersadda.Models.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.theartofdev.edmodo.cropper.CropImage
import kotlinx.android.synthetic.main.fragment_shell.*
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rena
//  me parameter arguments, choose names that match
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
    var pikedImageCode = 0
    lateinit var materialRadioBtn: RadioGroup
    var selectedmaterial: String? = null
    lateinit var pinShell: EditText
//    lateinit var chipsRadioButton: RadioButton
//    lateinit var humanRadioButton:RadioButton
//    lateinit var rawRadioButton: RadioButton

    lateinit var mContext: Context
    lateinit var mAuth: FirebaseAuth
    var imagesUriList: ArrayList<Uri>? = null
    var imagesUrlList: ArrayList<String>? = null
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        imagesUriList = ArrayList()
        imagesUrlList = ArrayList()
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
        vehicle = view!!.findViewById(R.id.vehicleShelEt)
        materialRadioBtn = view!!.findViewById(R.id.radioGroup)
        pinShell = view!!.findViewById(R.id.pinCodeID)


        val arrayAdapter =
            ArrayAdapter(
                activity!!,
                android.R.layout.simple_list_item_1,
                Registration.localitiesArray
            )
        localityId.setAdapter(arrayAdapter)
        val permissionGet = PermissionGet(context!!, activity!!)

        DealerResisterbtn.setOnClickListener {
            Toast.makeText(context, "button clicked", Toast.LENGTH_SHORT).show()
            uploadImagrToFirebase()

        }
        pic1Btn.setOnClickListener {

            val permissionName = Manifest.permission.READ_EXTERNAL_STORAGE
            if (permissionGet.checkperrmission(permissionName)) {
                Log.e("DB", "PERMISSION GRANTED")

                PickImage(1)
            }

        }

        pic2Btn.setOnClickListener {
            val permissionName = Manifest.permission.READ_EXTERNAL_STORAGE
            if (permissionGet.checkperrmission(permissionName)) {
                Log.e("DB", "PERMISSION GRANTED")
                PickImage(2)

            }
        }
        pic3Btn.setOnClickListener {
            val permissionName = Manifest.permission.READ_EXTERNAL_STORAGE
            if (permissionGet.checkperrmission(permissionName)) {
                Log.e("DB", "PERMISSION GRANTED")
                PickImage(3)

            }
        }
        DealerResisterbtn.setOnClickListener {
            if (vehicle.text.toString() != null && localityAutoCompleteTextView.text.toString() != null && pinCodeID.text.toString() != null && cityAdresEtShel.text.toString() != null && selectedmaterial != null) {
                uploadImagrToFirebase()
            }
        }
        materialRadioBtn.setOnCheckedChangeListener { compoundButton, b ->
            when (b) {
                R.id.ChipsRadioButton -> {
                    selectedmaterial = "Chips"

                }
                R.id.RawMaterialRadioButton -> {
                    selectedmaterial = "Rawmaterial"
                }
                R.id.humanRadioButton -> {
                    selectedmaterial = "Human"
                }

            }
        }

    }

    fun PickImage(code: Int) {
        Log.d("Main", "Try to show photo selecter")
        getContext()?.let {
            CropImage.activity()
                .start(it, this)
        };
        pikedImageCode = code
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("Main", "overrde activity result strat")
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            //proceed and check what thee image is selected
            Log.d("Main", "image is selected")

            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {

                when (pikedImageCode) {
                    1 -> {
                        Log.d("Main", "image is selected")
                        pic1Img.setImageURI(result.uri)
                        selected_photo1_uri = result.uri
                        imagesUriList?.add(selected_photo1_uri!!)
                        pic1Img.setImageURI(selected_photo1_uri)
                        pic1Btn.alpha = 0f

                    }
                    2 -> {
                        Log.d("Main", "image is selected")
                        selected_photo2_uri = result.uri
                        imagesUriList?.add(selected_photo2_uri!!)
                        pic2Img.setImageURI(selected_photo2_uri)
                        pic2Btn.alpha = 0f
                    }
                    3 -> {
                        Log.d("Main", "image is selected")
                        selected_photo3_uri = result.uri
                        imagesUriList?.add(selected_photo3_uri!!)
                        pic3Img.setImageURI(selected_photo3_uri)
                        pic3Btn.alpha = 0f
                    }

                }
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
                Log.d("Main", "$error")
            }

        } else {
            Log.d("Main", "conditioon false")
        }
    }

    /*
    for permission override
    */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Registration.PermissionCode -> {
                if (grantResults.size >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                        Toast.makeText(this,"permission granted",Toast.LENGTH_SHORT).show()
                    Registration.permissionGrant = true
                    Log.d("phone no", "Contact no:- trying to reed contacts")
                } else {
                    Registration.permissionGrant = false
                    Toast.makeText(context, "Permission denied in fragment ", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }


    private fun uploadImagrToFirebase() {
        val sharePrefUsersData = SharePrefUsersData(activity!!)
        val usersData = sharePrefUsersData.getUserDataPref()
        val userPicUri = Uri.parse(usersData.image)
        imagesUriList!!.add(userPicUri)
        for (uri in imagesUriList!!) {
            val filename = UUID.randomUUID().toString()
            val ref = FirebaseStorage.getInstance().getReference("/DealersImages/$filename")
            Log.d("Images UrI", "$uri")
            ref.putFile(uri).addOnSuccessListener {

                ref.downloadUrl.addOnSuccessListener {
                    Log.d("Images url", "${it}")
                    imagesUrlList!!.add(it.toString())
                    if (imagesUriList!!.size == imagesUrlList!!.size && imagesUrlList!!.isNotEmpty()) {
                        Toast.makeText(
                            context,
                            "images are uploded successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        saveDealersDataFb(usersData)
                    }
                }
            }
                .addOnFailureListener {
                    Log.d("Images url", "${it}+ failed to upload")
                }

        }


    }

    private fun saveDealersDataFb(usersData: UserData) {
        val uid = FirebaseAuth.getInstance().uid ?: ""
        val picPath1: String = imagesUrlList!!.get(0)
        val picPath2: String = imagesUrlList!!.get(1)
        val picPath3: String = imagesUrlList!!.get(2)
        val userPic: String = imagesUrlList!!.get(3)
        Log.d("Images url", " ${imagesUrlList!!.get(0)}")
        Log.d("Images url", " ${imagesUrlList!!.get(1)}")
        Log.d("Images url", " ${imagesUrlList!!.get(2)}+ $picPath1")
        val dealersDataModel = DealersDataModel(
            usersData.userNmae,
            usersData.PhoneNo,
            usersData.email,
            userPic,
            vehicle.text.toString(),
            selectedmaterial!!,
            localityAutoCompleteTextView.text.toString(),
            pinShell.text.toString(),
            cityAdresEtShel.text.toString(),
            uid, picPath1,
            picPath2,
            picPath3
        )
        val ref = FirebaseDatabase.getInstance().getReference("/DealersData/$uid")

        ref.setValue(dealersDataModel)
            .addOnSuccessListener {
                Log.d("Images url", "user detail is uploaded")
                Toast.makeText(context, "Dealers data saved succesfully", Toast.LENGTH_SHORT).show()
            }

            .addOnFailureListener {
                Log.d("Images url", "details re not uploaded :try again")
                Toast.makeText(context, "dealers details not uploaded", Toast.LENGTH_SHORT).show()

            }
    }


}




