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

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: ArrayAdapter<String>


    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

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
        val searchButton = view?.findViewById<Button>(R.id.searchButton)
        searchButton?.setOnClickListener {
            val query = searchView?.text.toString()
            // GoogleMapFragment에 검색어 전달
            val googleMapFragment =
                childFragmentManager.findFragmentById(R.id.mapContainer) as? GoogleMapFragment
            googleMapFragment?.onSearchQuery(query)
        }

        /*// Gluten Free 버튼 클릭 이벤트 처리
        binding.GlutenFreeButton.setOnClickListener {
            // Vegan 버튼이 클릭되었을 때 처리할 코드 추가
            val googleMapFragment = GoogleMapFragment.newInstance()
            googleMapFragment.showStoresOnMap("Gluten Free") // 가게 유형을 전달
        }

        // Vegan 버튼 클릭 이벤트 처리
        binding.VeganButton.setOnClickListener {
            val googleMapFragment = GoogleMapFragment.newInstance()
            googleMapFragment.showStoresOnMap("Vegan") // 가게 유형을 전달
        }

        // Lactose Intolerance 버튼 클릭 이벤트 처리
        binding.LactoseButton.setOnClickListener {
            val googleMapFragment = GoogleMapFragment.newInstance()
            googleMapFragment.showStoresOnMap("Lactose Intolerance") // 가게 유형을 전달
        }

        // Halal 버튼 클릭 이벤트 처리
        binding.HalalButton.setOnClickListener {
            val googleMapFragment = GoogleMapFragment.newInstance()
            googleMapFragment.showStoresOnMap("Halal") // 가게 유형을 전달
        }*/

        binding.VeganButton.setOnClickListener { showStoresOnMap("Vegan") }
        binding.GlutenFreeButton.setOnClickListener { showStoresOnMap("Glueten Free") }
        binding.LactoseButton.setOnClickListener { showStoresOnMap("Lactose Intolerance") }
        binding.HalalButton.setOnClickListener { showStoresOnMap("Halal") }

        return binding.root
    }

    private fun showStoresOnMap(storeType: String) {
        val googleMapFragment = GoogleMapFragment.newInstance()
        val stores = // fetch or provide a list of stores based on storeType
            googleMapFragment.showStoresOnMap(stores)
    }

    //fragment의 view가 소멸되는 시점에 호출
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}