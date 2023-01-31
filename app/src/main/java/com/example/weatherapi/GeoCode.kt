package com.example.weatherapi

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import java.io.IOException

class GeoCode(context: Context,lat: Double,lng: Double){

    private val geoCoder: Geocoder = Geocoder(context)
    private lateinit var address: String

    init {
        defineAddresses(lat, lng)
    }
    private fun defineAddresses(lat: Double, lng: Double){

        try {
            val addresses: List<Address> = geoCoder.getFromLocation(lat, lng, 1) as List<Address>
            println(addresses)
            val returnedAddress: Address = addresses.first()
            val strReturnedAddress = StringBuilder("")

            for (i in 0..returnedAddress.maxAddressLineIndex) {
                strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n")
            }

            setAddress(strReturnedAddress.toString())

            Log.d("My Current location ", strReturnedAddress.toString())

        } catch (e: IOException) {

            e.printStackTrace();
            Log.d("My Current location", "Canont get Address!");

        }

    }

    private fun setAddress(address: String)
    {
        this.address = address
    }
    fun getAddress(): String {
        return address
    }

}