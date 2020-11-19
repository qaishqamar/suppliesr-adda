package com.example.suppliersadda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
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
        fetchUserData()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        emailTv=view!!.findViewById(R.id.EmailDynamicTvProfile)
        userNameTv=view!!.findViewById(R.id.userNameDynamicTvProfile)
        phonNoTv=view!!.findViewById(R.id.PhonNoDynamicTvProfile)
        userPic=view!!.findViewById(R.id.profilePicProfile)
    }
fun fetchUserData(){
    val UserUid= FirebaseAuth.getInstance().uid
    val ref= FirebaseDatabase.getInstance().getReference("/usersData/$UserUid")
       ref.addValueEventListener(object: ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {

                  val userData= snapshot.getValue(UserData::class.java)
                   if (userData!=null){
                       Log.d("profile","${userData!!.userNmae}")
                       userNameTv.text=userData.userNmae
                       emailTv.text=userData.email
                       phonNoTv.text=userData.PhoneNo
                       if (userData.image!=null&&profilePicProfile!=null){
                           Picasso.get().load(userData.image).into(profilePicProfile)

                       }
                   }
                  else{
                       Log.d("profile","${userData} +null value")
                   }

           }

           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }

       })
}

}