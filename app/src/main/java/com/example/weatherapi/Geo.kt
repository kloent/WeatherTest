package com.example.weatherapi

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class Geo(private val context: Context){

    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    private var locationCallback: LocationCallback =  locationCallback()
    private var locationRequest: LocationRequest = createLocationRequest()
    private lateinit var location: Location
    var locationTracking: Boolean = false

    private fun setCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.


            Log.d("Geo","Permission failed")
        } else {

            fusedLocationClient.getCurrentLocation(100, object : CancellationToken() {
                override fun onCanceledRequested(p0: OnTokenCanceledListener) = CancellationTokenSource().token

                override fun isCancellationRequested() = false
            }).addOnSuccessListener { location: Location ->
                this.location = location
            }
        }
    }

    private fun setLastLocation() {


        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            println("Permissions failed Geo")
        }

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location ->
                println(location.latitude.toString() + "*********************************************")

                if(location.latitude != 0.0){
                    this.location = location

                } else
                {
                    setCurrentLocation()
                }
            }

    }

    fun startLocationUpdates(locationRequest: LocationRequest = this.locationRequest, locationCallback: LocationCallback = this.locationCallback) {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            println("Geo Error")
        }else
        {
            println("startLocUpdate")
            this.fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback, Looper.getMainLooper())
        }
    }

    private fun createLocationRequest(): LocationRequest {
        val locationRequest = LocationRequest.Builder(100000)
        return locationRequest.build()
    }

    private fun locationCallback(): LocationCallback{
        locationTracking = true
        return  object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations){
                    println("locationCallback")
                    this@Geo.setLoc(location)
                    this@Geo.locationTracking = false
                }
            }
        }
    }

    fun setLoc(location: Location) {
        this.location = location
    }

    fun getLocation(): Location {
        return this.location
    }

    fun stopLocationUpdates(locationCallback: LocationCallback = this.locationCallback){
        this.fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}


