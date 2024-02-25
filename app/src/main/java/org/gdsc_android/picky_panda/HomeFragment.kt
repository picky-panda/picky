package org.gdsc_android.picky_panda

import android.content.Context
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding
import com.google.android.gms.maps.*
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

    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        //ServiceApi 초기화
        serviceApi = ServiceCreator.apiService

        // FragmentTransaction을 사용하여 GoogleMapFragment 추가
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.mapContainer, GoogleMapFragment())
        fragmentTransaction.commit()

        // Adapter 초기화
        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line)

        // AutoCompleteTextView 설정
        val searchView = view?.findViewById<AutoCompleteTextView>(R.id.searchView)
        searchView?.setAdapter(adapter)

        // 검색 버튼 클릭 이벤트 처리
        binding.searchButton?.setOnClickListener {
            val query = binding.searchView?.text.toString()
            // GoogleMapFragment에 검색어 전달
            val googleMapFragment =
                childFragmentManager.findFragmentById(R.id.mapContainer) as? GoogleMapFragment
            googleMapFragment?.onSearchQuery(query)

            //가게 조회 API 호출
            //inquireStoresOnMap(query)
        }

        binding.VeganButton.setOnClickListener { showStoresOnMap("Vegan") }
        binding.GlutenFreeButton.setOnClickListener { showStoresOnMap("Glueten Free") }
        binding.LactoseButton.setOnClickListener { showStoresOnMap("Lactose Intolerance") }
        binding.HalalButton.setOnClickListener { showStoresOnMap("Halal") }

        return binding.root
    }
    //지도 경계 좌표값 가져오기
    fun inquireStoresOnMap(northEast: LatLng, southWest: LatLng, authToken: String) {

        // northEast와 southWest 좌표를 사용하여 가게 조회 API 호출
        val northEastX = northEast.latitude
        val northEastY = northEast.longitude
        val southWestX = southWest.latitude
        val southWestY = southWest.longitude

        //authToken을 헤더에 추가하여 API 요청
        serviceApi.inquireMap(authToken, northEastX, northEastY, southWestX, southWestY)
            .enqueue(object : Callback<ResponseInquireMapData> {
                override fun onResponse(
                    call: Call<ResponseInquireMapData>,
                    response: Response<ResponseInquireMapData>
                ) {
                    if (response.isSuccessful) {
                        // 가게 조회 성공 시의 처리
                        val responseData = response.body()

                        if (responseData != null){
                            if (responseData.code == 200){
                                //가게 목록이 비어있지 않을 경우
                                if (responseData.data != null) {
                                    for (restaurant in responseData.data) {
                                        // 가게 정보에서 위도(latitude)와 경도(longitude)를 추출
                                        val storeLatLng = LatLng(restaurant.latitude, restaurant.longitude)

                                        // 마커 추가
                                        googleMap?.addMarker(
                                            MarkerOptions()
                                                .position(storeLatLng)
                                                .title(restaurant.placeName)
                                                .snippet("ID: ${restaurant.id}")
                                        )
                                    }
                                } else{
                                    // 가게 목록이 비어있을 때의 처리
                                    Toast.makeText(requireContext(), "Cannot find any store.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseInquireMapData>, t: Throwable) {
                    // 네트워크 오류 등으로 인한 실패 시의 처리
                }
            })
    }


            private fun showStoresOnMap(storeType: String) {
                val googleMapFragment = GoogleMapFragment.newInstance()
                val stores = // fetch or provide a list of stores based on storeType
                    googleMapFragment.showStoresOnMap(store)
            }

                //fragment의 view가 소멸되는 시점에 호출
                override fun onDestroyView() {
                    super.onDestroyView()
                    _binding = null
                }
            }




