package org.gdsc_android.picky_panda

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class HomeFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private var currentMarker: Marker? = null

    private val DEFAULT_ZOOM = 15.0f

    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
       _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
       val rootView = binding.root

        val mapFragment = childFragmentManager.findFragmentById(binding.homeFragment.id) as SupportMapFragment
        mapFragment.getMapAsync(this)
        return rootView
   }

    //fragment의 view가 소멸되는 시점에 호출
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Map이 사용될 준비가 되었을 때 호출되는 메소드
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        googleMap.setOnMarkerClickListener(this)
        //구글맵 설정 및 초기화 작업 수행
        currentMarker = setupMarker(LatLngEntity(37.5574,126.9269)) //default 홍대입구역
        currentMarker?.showInfoWindow()

        //사용자가 주소를 입력하면 해당 위치로 지도를 이동시키는 로직 구현
        val address = "서울특별시 마포구 양화로"
        moveMapToAddress(address, requireContext())
    }

    //주소를 입력받아 지도를 해당 위치로 이동시키는 함수
    fun moveMapToAddress(address: String, context: Context){
        val location = geocodeAddress(address, context)
        if (location != null){
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(location, DEFAULT_ZOOM)
            googleMap.moveCamera(cameraUpdate)
        } else {
            //주소를 변환할 수 없는 경우에 대한 예외 처리
            Toast.makeText(context, "주소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    // 주소를 위도와 경도로 변환하는 함수
    fun geocodeAddress(address: String, context: Context): LatLng? {
        val geocoder = Geocoder(context)
        val addresses = geocoder.getFromLocationName(address, 1)
        if (addresses != null) {
            if (addresses.isNotEmpty()) {
                val latitude = addresses[0].latitude
                val longitude = addresses[0].longitude
                return LatLng(latitude, longitude)
            }
        }
        return null
    }

    //선택한 위치의 marker 표시
    //옵션 선택?
    private fun setupMarker(locationLatLngEntity: LatLngEntity): Marker? {

        val positionLatLng = LatLng(locationLatLngEntity.latitude!!,locationLatLngEntity.longitude!!)
        val markerOption = MarkerOptions().apply {
            position(positionLatLng)
            title("위치")
            snippet("홍대입구역 위치")
        }

        googleMap.mapType = GoogleMap.MAP_TYPE_NORMAL  // 지도 유형 설정
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positionLatLng, 15f))  // 카메라 이동
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15f))  // 줌의 정도 - 1 일 경우 세계지도 수준, 숫자가 커질 수록 상세지도가 표시됨
        return googleMap.addMarker(markerOption)

    }

    override fun onMarkerClick(marker: Marker): Boolean {
        //마커를 클릭했을 때 동작 구현
        //선택된 가게에 대한 정보를 하단에 표시하는 로직 구현
        //저장 기능 삽입 필요
        return true
    }


    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }
    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }
    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }
    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    data class LatLngEntity(
        var latitude: Double?,
        var longitude: Double?
    )


}

