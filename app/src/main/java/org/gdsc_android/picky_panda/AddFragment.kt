package org.gdsc_android.picky_panda

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.gdsc_android.picky_panda.databinding.FragmentAddBinding
import org.gdsc_android.picky_panda.databinding.FragmentMyPageBinding

class AddFragment : Fragment() {
    private lateinit var binding: FragmentAddBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 각 EditText의 입력 여부에 따라 NextButton 활성화/비활성화 설정
        binding.placeNameEditTextText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkInputsAndEnableButton()
            }
        })
        binding.editTextTextPostalAddress.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                checkInputsAndEnableButton()
            }
        })
        //nextButton 누르면 정보 리스트에 담고 AddDetailFragment로 이동
        binding.nextButton.setOnClickListener {
            val placeName = binding.placeNameEditTextText.text.toString()
            val editTextTextPostalAddress = binding.editTextTextPostalAddress.text.toString()
            (activity as MainActivity).replaceFragment(AddDetailFragment())
        }
    }

    private fun checkInputsAndEnableButton() {
        // 두 EditText의 입력 여부에 따라 Button 활성화/비활성화 설정
        val isPlaceNameNotEmpty = binding.placeNameEditTextText.text.isNotBlank()
        val isPostalAddressNotEmpty = binding.editTextTextPostalAddress.text.isNotBlank()

        binding.nextButton.isEnabled = isPlaceNameNotEmpty && isPostalAddressNotEmpty
    }
}