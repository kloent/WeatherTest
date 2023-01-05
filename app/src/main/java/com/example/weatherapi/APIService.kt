package com.example.weatherapi

import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface APIService {
    @GET("forecast?")
     fun getForecast(@Query("latitude") latitude: Double, @Query("longitude") longitude: Double): Call<JsonMeteo>


}