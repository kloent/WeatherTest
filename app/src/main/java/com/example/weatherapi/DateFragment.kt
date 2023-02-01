package com.example.weatherapi


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapi.databinding.DateFragmentBinding
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.TypeFilter
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import android.Manifest.permission
import android.app.appsearch.observer.ObserverCallback
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class DateFragment : Fragment() {

    companion object {
        //fun newInstance() = DateFragment()
    }

    private lateinit var binding: DateFragmentBinding
    private lateinit var viewModel: DateViewModel
    private lateinit var geo: Geo
    private lateinit var geoCode: GeoCode
    private lateinit var city: City
    private lateinit var meteoApi: MeteoApi

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

        viewModel = ViewModelProvider(this)[DateViewModel::class.java]
        binding.dateViewModel = viewModel
        binding.lifecycleOwner = this

        city = City()
        geoCode = GeoCode(requireContext())
        meteoApi = MeteoApi()

        // When change location set Name and Weather for City
        city.location.observe(this, Observer{location ->

            city.setName(geoCode.defineAddresses(location.latitude, location.longitude))
            meteoApi.setWeather(location.latitude,location.longitude)
            city.setWeather(meteoApi.getWeather())

        })

        //Observe when change name in City, change name in layout
        city.name.observe(this,Observer{name ->

            binding.cityName.text = name

        })

        autocomplete()

        //test Button
        binding.Geo.setOnClickListener {

        }

        return binding.root
    }

    override fun onPause() {
        super.onPause()
        println("StopLocUpd")
        geo.stopLocationUpdates()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perm = Permissions(requireContext(), requireActivity())
        //check permissions
        let {
            perm.setupPermissions(arrayOf(
            permission.ACCESS_COARSE_LOCATION,
            permission.ACCESS_FINE_LOCATION,
            permission.WRITE_EXTERNAL_STORAGE,
            permission.READ_EXTERNAL_STORAGE
        )) }

        geo = Geo(requireContext())
        // start check of changing location
        geo.startLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        geo.startLocationUpdates()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    private fun autocomplete(){

        // Initialize the SDK
        Places.initialize(requireContext(),  getString(R.string.apiKey) )

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =  childFragmentManager.findFragmentById(R.id.autocomplete_fragment)
                    as AutocompleteSupportFragment

        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.NAME, Place.Field.LAT_LNG)).setTypeFilter(TypeFilter.CITIES)

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.

                Log.i(TAG, "Place: ${place.name}")

                city.setLocation(place.latLng!!.latitude, place.latLng!!.longitude)

                city.setName(place.name!!.toString())

            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

}
