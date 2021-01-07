package com.example.suppliersadda.Models

import java.util.ArrayList

class DealersDataModel(
    val userName:String,
    val userNo:String,
    val userEmail:String,
    val userImage:String,
    val vehicleProf: String,
    val transMaterial: String,
    val locality: String,
    val pin: String,
    val city: String,
    val uid: String,
    val imageFirst:String,
    val imageSecond: String,
    val imageThird: String
) {

    constructor():this("","","","","", "", "", "", "", "", "", "","")
}