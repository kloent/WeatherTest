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

        if(!geo.locationTracking){
            city.setLat(geo.getLocation().latitude)
            city.setLng(geo.getLocation().longitude)

            geoCode = GeoCode(requireContext(), city.getLat(), city.getLng())
            city.setName(geoCode.getAddress())

            meteoApi = MeteoApi(city.getLat(), city.getLng())

            city.setWeather(meteoApi.getWeather())
        }

        autocomplete()

        //test Button
        binding.Geo.setOnClickListener {
            city.getWeather()
        }
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val perm = Permissions(requireContext(), requireActivity())

        perm.setupPermissions(arrayOf(
            permission.ACCESS_COARSE_LOCATION,
            permission.ACCESS_FINE_LOCATION
        ))

        geo = Geo(requireContext())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // TODO: Use the ViewModel
    }

    fun autocomplete(){

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
                println( place.latLng)
                Log.i(TAG, "Place: ${place.name}")
                city.setLat(place.latLng!!.latitude)
                city.setLng(place.latLng!!.longitude)
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

}
