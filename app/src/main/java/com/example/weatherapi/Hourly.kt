package com.example.weatherapi

import com.google.gson.annotations.SerializedName


data class Hourly (

  @SerializedName("time"           ) var time          : ArrayList<String> = arrayListOf(),
  @SerializedName("temperature_2m" ) var temperature2m : ArrayList<Int>    = arrayListOf()

)