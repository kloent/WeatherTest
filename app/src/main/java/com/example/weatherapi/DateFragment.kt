package com.example.weatherapi


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapi.databinding.DateFragmentBinding
import java.util.*


class DateFragment : Fragment() {


    companion object {
        fun newInstance() = DateFragment()
    }
    private lateinit var binding: DateFragmentBinding
    private lateinit var viewModel: DateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.date_fragment,
            container,
            false
        )
        val geo = Geo(context!!)
        val meteo = MeteoApi()

        Log.i("GeoLogAndLate", geo.getWeatherForCurrentLocation().toString())
        binding.Geo.setOnClickListener { meteo.getWeather(geo.currentLoc)  }
        viewModel = ViewModelProvider(this)[DateViewModel::class.java]
        val today = Calendar.getInstance()
        binding.dateViewModel = viewModel
        binding.lifecycleOwner = this

        binding.firstDay.init(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            viewModel.changeDate()
        }
        binding.secondDay.init(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH)
        ) { view, year, monthOfYear, dayOfMonth ->
            viewModel.changeDate()
        }

        //binding.firstDay.init(today.get(Calendar.YEAR),today.get(Calendar.MONTH),today.get(Calendar.DAY_OF_MONTH))

        //return inflater.inflate(R.layout.date_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        // TODO: Use the ViewModel
    }

}
