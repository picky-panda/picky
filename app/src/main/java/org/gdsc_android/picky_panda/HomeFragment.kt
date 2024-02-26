package org.gdsc_android.picky_panda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.gdsc_android.picky_panda.data.ResponseInquireMapData
import org.gdsc_android.picky_panda.data.ServiceApi
import org.gdsc_android.picky_panda.data.ServiceCreator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var serviceApi: ServiceApi
    private var googleMap: GoogleMap? = null
    private lateinit var selectedCategory: String

    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        //init ServiceApi
        serviceApi = ServiceCreator.apiService

        // make GoogleMapFragment using FragmentTransaction
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mapContainer, GoogleMapFragment())
        fragmentTransaction.commit()

        // init Adapter
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line)

        // AutoCompleteTextView setting
        val searchView = view?.findViewById<AutoCompleteTextView>(R.id.searchView)
        searchView?.setAdapter(adapter)

        // click event on search button
        binding.searchButton?.setOnClickListener {
            val query = binding.searchView?.text.toString()
            // convey search words to GoogleMapFragent
            val googleMapFragment =
                childFragmentManager.findFragmentById(R.id.mapContainer) as? GoogleMapFragment
            googleMapFragment?.onSearchQuery(query)
        }

        binding.VeganButton.setOnClickListener { selectedCategory("Vegan") }
        binding.GlutenFreeButton.setOnClickListener { selectedCategory("Glueten Free") }
        binding.LactoseButton.setOnClickListener { selectedCategory("Lactose Intolerance") }
        binding.HalalButton.setOnClickListener { selectedCategory("Halal") }

        return binding.root
    }

    // get map boundary coordinate values
    fun inquireStoresOnMap(northEast: LatLng, southWest: LatLng, authToken: String) {

        val northEastX = northEast.latitude
        val northEastY = northEast.longitude
        val southWestX = southWest.latitude
        val southWestY = southWest.longitude

        // request API by adding authToken to header
        serviceApi.inquireMap("Bearer $authToken", northEastX, northEastY, southWestX, southWestY)
            .enqueue(object : Callback<ResponseInquireMapData> {
                override fun onResponse(
                    call: Call<ResponseInquireMapData>,
                    response: Response<ResponseInquireMapData>
                ) {
                    if (response.isSuccessful) {
                        //if success
                        val responseData = response.body()

                        if (responseData != null){
                            if (responseData.code == 200){
                                if (responseData.data != null) {
                                    for (restaurant in responseData.data) {
                                        // take latitude and longitude from map
                                        val storeLatLng = LatLng(restaurant.latitude, restaurant.longitude)

                                        // make marker
                                        googleMap?.addMarker(
                                            MarkerOptions()
                                                .position(storeLatLng)
                                                .title(restaurant.placeName)
                                                .snippet("ID: ${restaurant.id}")
                                        )
                                    }
                                } else{
                                    // if there is no list of stores
                                    Toast.makeText(requireContext(), "Cannot find any store.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseInquireMapData>, t: Throwable) {
                    Log.d("GoogleMap", "Letwork prolem")

                }
            })
    }


    private fun selectedCategory(category: String) {
        selectedCategory = category
        // marker of selected category
        showStoresOnMap()
    }

    private fun showStoresOnMap() {

        // call showStoresOnMap fun in GoogleMapFragment
        val googleMapFragment = GoogleMapFragment.newInstance()
        selectedCategory?.let {
            googleMapFragment.showStoresOnMap(it)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

}

