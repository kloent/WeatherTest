package com.example.weatherapi

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.IOException

class GeoCode(context: Context){

    private val geoCoder: Geocoder = Geocoder(context)

     fun defineAddresses(lat: Double, lng: Double): String {

        try {
            val addresses: List<Address> = geoCoder.getFromLocation(lat, lng, 1) as List<Address>
            println(addresses)
            val returnedAddress: Address = addresses.first()
            val strReturnedAddress = StringBuilder("")

            for (i in 0..returnedAddress.maxAddressLineIndex) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
            }

            Log.d("My Current location ", strReturnedAddress.toString())
            return strReturnedAddress.toString()

        } catch (e: IOException) {

            e.printStackTrace();

            Log.d("My Current location", "Canont get Address!");
            return ""

        }

    }


}