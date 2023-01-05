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

    private val _firstDay = MutableLiveData<DatePicker.OnDateChangedListener>()
    val firstDay: LiveData<DatePicker.OnDateChangedListener>
        get() = _firstDay

    private val _secondDay = MutableLiveData<Date>()
    val secondDay: LiveData<Date>
    get() = _secondDay

    private val _city = MutableLiveData<String>()
    val city: LiveData<String>
    get() = _city
    init{
        changeDate()
    }
    fun changeDate(){
        Log.i("Change","changeDate")
    }

}