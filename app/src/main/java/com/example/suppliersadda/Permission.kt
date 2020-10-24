package com.example.suppliersadda

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.security.Permission

class PermissionGet(val context:Context,val activity: Activity) {


    fun checkperrmission(permissionName:String):Boolean{
        val permission= ContextCompat.checkSelfPermission(context,
            android.Manifest.permission.READ_EXTERNAL_STORAGE)

       if(permission== PackageManager.PERMISSION_GRANTED)
       {

          return true
       }
       else
       {
           ActivityCompat.requestPermissions(activity, arrayOf(permissionName), Registration.PermissionCode)

             return Registration.permissionGrant
       }
    }








}