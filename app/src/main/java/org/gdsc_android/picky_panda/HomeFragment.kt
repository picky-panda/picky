package org.gdsc_android.picky_panda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
       _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
       return binding.root
   }

    //fragment의 view가 소멸되는 시점에 호출
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    //Map이 사용될 준비가 되었을 때 호출되는 메소드
    override fun onMapReady(p0: GoogleMap?) {
        TODO("Not yet implemented")
    }


}

