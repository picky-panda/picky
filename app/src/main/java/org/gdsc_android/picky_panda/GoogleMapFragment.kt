package org.gdsc_android.picky_panda

import android.app.AlertDialog
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

        // MapView 초기화 및 설정
        mapView = view.findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync { map ->
            googleMap = map

            // GoogleMap 초기화 및 설정
            configureMapSettings()

            // 마커 추가
            val markerOptions = MarkerOptions()
                .position(LatLng(37.5569, -126.9239)) // 마커의 위치
                .title("마커 제목") // 마커의 제목
            val marker = googleMap?.addMarker(markerOptions)

            // 마커 클릭 리스너 등록
            googleMap?.setOnMarkerClickListener { clickedMarker ->
                // 마커 클릭 시 동작
                // 여기에서 마커에 대한 상세 정보를 표시하는 등의 동작을 구현할 수 있습니다.
                true
            }
        }

        return view
    }


    private fun configureMapSettings() {
        // GoogleMap 설정을 추가로 진행
        googleMap?.uiSettings?.isZoomControlsEnabled = true
        googleMap?.uiSettings?.isMyLocationButtonEnabled = true
        // 기타 설정들을 추가할 수 있습니다.
    }

    fun onSearchQuery(query: String?) {
        // query를 사용하여 지도에 위치를 표시하거나 다른 동작 수행
        // 이 부분에서 Google Places API 등을 사용하여 위치를 검색하고 표시할 수 있습니다.
        if (query.isNullOrEmpty()) {
            return
        }

        val geocoder = Geocoder(requireContext())
        try {
            // 주소를 좌표로 변환
            val locationList = geocoder.getFromLocationName(query, 1)
            if (locationList != null) {
                if (locationList.isNotEmpty()) {
                    val latitude = locationList?.get(0)?.latitude
                    val longitude = locationList?.get(0)?.longitude

                    // 변환된 좌표로 지도 이동
                    val location = latitude?.let { longitude?.let { it1 -> LatLng(it, it1) } }
                    googleMap?.moveCamera(CameraUpdateFactory.newLatLng(location))

                    // 변환된 좌표에 마커 추가
                    googleMap?.addMarker(MarkerOptions().position(location!!).title(query))
                } else {
                    // 변환된 좌표가 없을 경우 처리
                    Toast.makeText(requireContext(), "Location not found", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }


        // 검색 결과 위치로 지도 이동
        val location = LatLng(37.5569, -126.9239)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(location))

        // 검색 결과 위치에 마커 추가
        googleMap?.addMarker(MarkerOptions().position(location).title(query))
    }

    companion object {
        fun newInstance(): GoogleMapFragment {
            return GoogleMapFragment()
        }
    }

    // 가게 목록을 표시하는 함수
    fun showStoresOnMap(storeList: List<Store>) {
        // 구글맵 관련 설정 및 초기화 등의 코드 추가

        // 가게 목록을 순회하며 마커를 추가하는 코드
        for (store in storeList) {
            val markerOptions = MarkerOptions()
                .position(LatLng(store.latitude, store.longitude))
                .title(store.name)

            val marker = googleMap?.addMarker(markerOptions)

            // 확장 함수를 사용하여 마커 클릭 이벤트 처리
            marker?.setOnMarkerClickListener { clickedMarker ->
                // 마커 클릭 이벤트 처리 코드
                openStoreDetailsFragment(store.name)
                true
            }
        }

    }

    // 상세 정보 Fragment를 열어주는 함수
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




