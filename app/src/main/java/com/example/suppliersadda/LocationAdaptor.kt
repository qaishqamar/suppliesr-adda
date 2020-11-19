package com.example.suppliersadda

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

//class LocationAdaptor(private val context: Activity,val locationList:Array<String> )  : ArrayAdapter<Any>(context, R.layout.location_unit,locationList) {
//    override fun getCount(): Int {
//        return locationList.size
//    }
//    override fun getItem(position: Int): Any{
//        return position
//    }
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//
//        override fun getView(position: Int, view: View?, parent: ViewGroup): View {
//            val inflater = context.layoutInflater
//            val rowView = inflater.inflate(R.layout.location_unit, null, true)
//
//            val locationText = rowView.findViewById(R.id.locatoinTextUnit) as TextView
//            locationText.text=locationList[position]
//
//
//
//            return rowView
//        }
//
//}