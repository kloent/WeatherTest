package com.example.weatherapi

import android.text.format.DateUtils
import android.util.Log
import android.widget.DatePicker
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class DateViewModel : ViewModel() {

    private val _chosenDay = MutableLiveData<DatePicker.OnDateChangedListener>()
    val chosenDay: LiveData<DatePicker.OnDateChangedListener>
        get() = _chosenDay

    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
    get() = _city

    fun changeDate(){
        Log.i("Change","changeDate")
    }


}