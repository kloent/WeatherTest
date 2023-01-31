package com.example.weatherapi

open class City() {
    private var name: String = ""
    private var lat: Double = 0.0
    private var lng: Double = 0.0
    private var weather = JsonMeteo()

    fun setName(name: String){
        this.name = name
    }

    fun getName(): String {
        return this.name
    }

    fun setLat(lat: Double){
        this.lat = lat
    }

    fun getLat(): Double {
        return this.lat
    }

    fun setLng(lng:Double){
        this.lng = lng
    }

    fun getLng(): Double {
        return this.lng
    }
    fun setWeather(body: JsonMeteo?) {
        if (body != null) {
            this.weather = body
        }
    }
    fun getWeather(): JsonMeteo {
        return this.weather
    }



}

