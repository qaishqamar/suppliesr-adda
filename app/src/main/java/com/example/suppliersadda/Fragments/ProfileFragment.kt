package com.example.suppliersadda.Fragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.suppliersadda.R
import com.example.suppliersadda.Models.SharePrefUsersData
import kotlinx.android.synthetic.main.fragment_profile.*


class ProfileFragment : Fragment() {
    lateinit var userNameTv:TextView
    lateinit var emailTv:TextView
    lateinit var phonNoTv:TextView
    lateinit var userPic:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        emailTv=view!!.findViewById(R.id.EmailDynamicTvProfile)
        userNameTv=view!!.findViewById(R.id.userNameDynamicTvProfile)
        phonNoTv=view!!.findViewById(R.id.PhonNoDynamicTvProfile)
        userPic=view!!.findViewById(R.id.profilePicProfile)
        fetchUserData()
    }
fun fetchUserData(){
//    val registration=Registration()
    val sharePrefUsersData= SharePrefUsersData(activity!!)
    val userData=sharePrefUsersData.getUserDataPref()
    if (userData!=null){
        Log.d("profile","${userData!!.userNmae}")
        userNameTv.text=userData.userNmae
        emailTv.text=userData.email
        phonNoTv.text=userData.PhoneNo
        if (userData.image!=null&&profilePicProfile!=null){
            val uri=Uri.parse(userData.image)
            profilePicProfile.setImageURI(uri)
                      }

    }

//    val UserUid= FirebaseAuth.getInstance().uid
//    val ref= FirebaseDatabase.getInstance().getReference("/usersData/$UserUid")
//       ref.addValueEventListener(object: ValueEventListener {
//           override fun onDataChange(snapshot: DataSnapshot) {
//
//                  val userData= snapshot.getValue(UserData::class.java)
//                   if (userData!=null){
//                       Log.d("profile","${userData!!.userNmae}")
//                       userNameTv.text=userData.userNmae
//                       emailTv.text=userData.email
//                       phonNoTv.text=userData.PhoneNo
//                       if (userData.image!=null&&profilePicProfile!=null){
//                           Picasso.get().load(userData.image).into(profilePicProfile)
//
//                       }
//                   }
//                  else{
//                       Log.d("profile","${userData} +null value")
//                   }
//
//           }
//
//           override fun onCancelled(error: DatabaseError) {
//               TODO("Not yet implemented")
//           }
//
//       })
}

}