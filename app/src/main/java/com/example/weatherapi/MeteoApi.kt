package com.example.weatherapi

import android.util.Log
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MeteoApi() {

    private val BASE_URL = "https://api.open-meteo.com/v1/"
    private var apiService: APIService
    init {

        val gson = GsonBuilder()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        apiService = retrofit.create(APIService::class.java)

    }

    fun getWeather(data: List<Double>)
    {
       val result = apiService.getForecast(data[0],data[1])

      result.enqueue(object : Callback<JsonMeteo?> {
            override fun onResponse(call: Call<JsonMeteo?>, response: Response<JsonMeteo?>) {
                //Do something with response
                println(response.body())
            }

            override fun onFailure(call: Call<JsonMeteo?>, t: Throwable) {
                println("Error")
                //Do something with failure
            }
        })


    }
}