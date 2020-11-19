package com.example.suppliersadda

import java.util.ArrayList

class DealersDataModel(
    val vehicleProf: String,
    val TransMaterial: String,
    val locality: String,
    val pin: String,
    val city: String,
    val uid: String,
    val ImageFirst:String,
    val ImageSecond: String,
    val ImageThird: String
) {

    constructor():this("", "", "", "", "", "", "", "","")
}