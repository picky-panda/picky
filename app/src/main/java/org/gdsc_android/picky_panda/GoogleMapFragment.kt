package org.gdsc_android.picky_panda

import android.app.AlertDialog
import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.net.PlacesClient
import org.gdsc_android.picky_panda.databinding.FragmentGoogleMapBinding
import java.io.IOException

class GoogleMapFragment : Fragment() {

    private var _binding: FragmentGoogleMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null
    /*private lateinit var placesClient: PlacesClient*/

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_google_map, container, false)

        // MapView init and setting
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            googleMap = map

            configureMapSettings()

            // moving map
            map.setOnCameraIdleListener {
                //callback when the map has finish moving
                val visibleRegion = map.projection.visibleRegion
                val northEast = visibleRegion.latLngBounds.northeast
                val southWest = visibleRegion.latLngBounds.southwest

                // take a token from SharedPreferences
                val sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
                val accessToken = sharedPreferences.getString("accessToken", "")
                val homeFragment = HomeFragment()

                homeFragment.inquireStoresOnMap(northEast, southWest, "Bearer $accessToken")
            }

        }

        return view
    }


    private fun configureMapSettings() {
        // additional GoogleMap setting
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        // other settings
    }

    fun onSearchQuery(query: String?) {
        // Use query to display locations on a map or perform other actions
        // can search the address using Google API
        if (query.isNullOrEmpty()) {
            return
        }

        val geocoder = Geocoder(requireContext())
        try {
            // convert address to coordinate
            val locationList = geocoder.getFromLocationName(query, 1)
            if (locationList != null) {
                if (locationList.isNotEmpty()) {
                    val latitude = locationList?.get(0)?.latitude
                    val longitude = locationList?.get(0)?.longitude

                    // moving map to converted coordinate
                    val location = latitude?.let { longitude?.let { it1 -> LatLng(it, it1) } }
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLng(location))

                    // make marker
                    googleMap?.addMarker(MarkerOptions().position(location!!).title(query))
                } else {
                    // when there is no coordinate
                    Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        fun newInstance(): GoogleMapFragment {
            return GoogleMapFragment()
        }
    }

    fun showStoresOnMap(it: String) {

    }

    // opening the fragment of the details
    private fun openStoreDetailsFragment(storeName: String) {
        val fragment = StoreDetailsFragment.newInstance(storeName)
        val transaction = fragmentManager?.beginTransaction()
        transaction?.replace(R.id.storeDetailsContainer, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }


    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

}

private fun Marker?.setOnMarkerClickListener(listener: ((Marker) -> Boolean)?) {
    this?.setOnMarkerClickListener(listener)
}




