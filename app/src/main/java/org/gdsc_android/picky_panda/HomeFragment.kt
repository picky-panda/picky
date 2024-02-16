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
    //실제로 사용할 수 있는 프로퍼티.
    //_binding의 null 여부를 확인하고 non-null인 경우에만 해당 바인딩 정보를 반환
    private val binding get() = _binding!!

   //fragment가 최초로 화면에 표시될 때 호출되는 메서드
    //_binding 변수 초기화 및 해당 fragment의 xml 레이아웃을 인플레이트하여 view 생성 후 반환
    override fun onCreateView(
       inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
   ): View? {
       _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
       return binding.root
   }

    //fragment의 view가 소멸되는 시점에 호출
    //이때 _binding을 null로 설정시켜 메모리 누수를 방지한다 fragment의 view가 다시 생성될 때 _binding은 다시 초기화 된다
    override fun onDestroyView() {
        super.onDestroyView() //부모 클래스인 Fragment 클래스의 onDestroyViewㄹ르 호출. 소멸 전 필요한 작업 수행.
        _binding = null
    }

    //Map이 사용될 준비가 되었을 때 호출되는 메소드
    override fun onMapReady(p0: GoogleMap?) {
        TODO("Not yet implemented")
    }


}

