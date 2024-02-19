package org.gdsc_android.picky_panda

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.children
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import org.gdsc_android.picky_panda.AddDetailFragment
import org.gdsc_android.picky_panda.databinding.FragmentAddDetailBinding

class AddDetailFragment : Fragment() {
    private lateinit var binding: FragmentAddDetailBinding
    private val selectedChipIds = mutableSetOf<Int>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.placeCategory,
            android.R.layout.simple_spinner_item
        )
        binding.placeCategorySpinner.adapter = adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        //각 Chip의 체크 상태 변경을 감지하고, 그에 따라 selectedChipIds를 업데이트
        binding.optionChipGroup.children.forEach { view ->
            (view as? Chip)?.setOnCheckedChangeListener { chip, isChecked ->
                if (isChecked) {
                    selectedChipIds.add(chip.id)
                } else {
                    selectedChipIds.remove(chip.id)
                }
                checkSubmitButtonEnable()
                onAllChipsDeselected()
            }
        }

        binding.placeCategorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                checkSubmitButtonEnable()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                checkSubmitButtonEnable()
            }
        }

        binding.submitButton.setOnClickListener {
            showToast("Submitted!")

            // 현재 ChipGroup에서 선택된 Chip들의 ID들을 selectedChipIds라는 리스트에 담음
            val selectedChipIds = binding.optionChipGroup.checkedChipIds
            //해당 ID를 사용해서 Chip을 찾아 selectedChip에 저장
            for (chipId in selectedChipIds) {
                val selectedChip = binding.root.findViewById<Chip>(chipId)
            }
            //사용자가 입력한 음식의 종류를 whatKindOfFoodEntered에 저장
            val whatKindOfFoodEntered = binding.whatKindOfFoodEditTextText.text.toString()
            //스피너에서 선택된 카테고리를 selectedCategory에 저장
            val selectedCategory = binding.placeCategorySpinner.selectedItem as String

            // AddFragment로 이동
            (activity as MainActivity).replaceFragment(AddFragment())
        }

        binding.editButton.setOnClickListener {
            // AddFragment로 이동
            (activity as MainActivity).replaceFragment(AddFragment())
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun checkSubmitButtonEnable() {
        val isAtLeastOneChipSelected = binding.optionChipGroup.checkedChipIds.isNotEmpty()
        val isSpinnerItemSelected = binding.placeCategorySpinner.selectedItemPosition != AdapterView.INVALID_POSITION

        binding.submitButton.isEnabled = isAtLeastOneChipSelected && isSpinnerItemSelected
    }

    private fun onAllChipsDeselected() {
        if (binding.optionChipGroup.checkedChipIds.isNullOrEmpty() ) {
            binding.submitButton.isEnabled = false
            showToast("Please select at least one option.")
        }
    }
}