package com.example.weatherapi

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

open class City {

    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _location = MutableLiveData<Location>()
    val location: LiveData<Location>
        get() = _location

    private val _weather = MutableLiveData<JsonMeteo>()
    val weather: LiveData<JsonMeteo>
    get() =_weather

    fun setName(city: String) {
        _name.value = city
    }

    fun setLocation(lat: Double, lng: Double){

        val loc: Location = Location("loc")

        loc.latitude = lat
        loc.longitude = lng

        _location.value = loc
    }

    fun setWeather(body: JsonMeteo?) {
        if (body != null) {
            _weather.value = body
        }
    }




}

