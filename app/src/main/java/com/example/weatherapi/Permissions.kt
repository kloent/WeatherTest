package com.example.weatherapi

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.awaitAll

class Permissions(private val context: Context , private val activity: Activity) : AppCompatActivity() {

    private val TAG = "PermissionDemo"
    private val RECORD_REQUEST_CODE = 101


    fun setupPermissions(perms: Array<String>) {

        for (str in perms) {
            val permission = ContextCompat.checkSelfPermission(context,
                str)

            if (permission != PackageManager.PERMISSION_GRANTED) {
                Log.i(TAG, "Permission to record denied: $str")
                makeRequest(perms)
            }
        }
    }

    private fun makeRequest(perms: Array<String>) {
        ActivityCompat.requestPermissions(activity,
            perms,
            RECORD_REQUEST_CODE)
        return
    }
    override fun onRequestPermissionsResult(requestCode: Int,
                                             permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RECORD_REQUEST_CODE -> {

                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {

                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }
}