package org.gdsc_android.picky_panda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding
import org.gdsc_android.picky_panda.databinding.FragmentStoreDetailsBinding

class StoreDetailsFragment : Fragment() {

    private var _binding: FragmentStoreDetailsBinding? = null
    private val binding get() = _binding!!
    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreDetailsBinding.inflate(layoutInflater, container, false)

        //상세정보를 표시하는 UI를 구성


        //버튼 클릭 시 detail화면으로 이동
        binding.gotoDetailButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(DetailFragment())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 뷰바인딩을 통한 버튼 클릭 이벤트 처리
        binding.bookmarkButton.setOnClickListener {
            // 버튼의 상태 변경
            isBookmarked = !isBookmarked
            updateBookmarkButtonState()
            // 기타 클릭 이벤트 처리 코드
        }
    }

    private fun updateBookmarkButtonState() {
        // 버튼의 상태에 따라 background 변경
        val backgroundResId = if (isBookmarked) {
            R.drawable.baseline_bookmark_24
        } else {
            R.drawable.baseline_bookmark_border_24
        }
        binding.bookmarkButton.setBackgroundResource(backgroundResId)
    }

    companion object {
        private const val ARG_STORE = "store"

        fun newInstance(storeName: String): StoreDetailsFragment {
            val fragment = StoreDetailsFragment()
            val args = Bundle()
            args.putString(ARG_STORE, storeName)
            fragment.arguments = args
            return fragment
        }
    }
}