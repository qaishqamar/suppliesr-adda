package com.example.suppliersadda.Models

import android.media.Image

class UserData(val uid:String,val userNmae:String,val PhoneNo:String,val email:String,val image:String) {
     constructor():this("","","","","")
}