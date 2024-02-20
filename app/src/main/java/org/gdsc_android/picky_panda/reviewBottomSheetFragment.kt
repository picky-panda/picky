package org.gdsc_android.picky_panda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding
import org.gdsc_android.picky_panda.databinding.FragmentReviewBottomSheetBinding


class reviewBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentReviewBottomSheetBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bottom Sheet 다이얼로그의 내용에 대한 초기화 및 이벤트 처리
        val reviewEditText = view.findViewById<EditText>(R.id.reviewEditText)
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        val closeButton = view.findViewById<Button>(R.id.closeButton)

        submitButton.setOnClickListener {
            // 리뷰 제출 버튼 클릭 이벤트 처리
            val reviewText = reviewEditText.text.toString()
            // 여기서 리뷰를 사용할 수 있습니다.
            // 예: 저장, 네트워크 전송 등의 작업 수행
            dismiss() // 다이얼로그 닫기
        }

        closeButton.setOnClickListener {
            // 닫기 버튼 클릭 이벤트 처리
            dismiss() // 다이얼로그 닫기
        }
    }


    companion object {

    }
}