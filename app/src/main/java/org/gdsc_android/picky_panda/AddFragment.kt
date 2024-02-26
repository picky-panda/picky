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

        // Set the NextButton to be enabled/disabled depending on whether each EditText has been filled in
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
        //NextButton is pressed, store the information in a list and move to the AddDetailFragment
        binding.nextButton.setOnClickListener {
            val placeName = binding.placeNameEditTextText.text.toString()
            val editTextTextPostalAddress = binding.editTextTextPostalAddress.text.toString()
            val bundle = Bundle().apply {
                putString("placeName", placeName)
                putString("postalAddress", editTextTextPostalAddress)
            }

            val addDetailFragment = AddDetailFragment().apply {
                arguments = bundle
            }
            (activity as MainActivity).replaceFragment(AddDetailFragment())
        }
    }

    private fun checkInputsAndEnableButton() {
        // Set the Button to be enabled/disabled depending on whether the two EditTexts have been filled in
        val isPlaceNameNotEmpty = binding.placeNameEditTextText.text.isNotBlank()
        val isPostalAddressNotEmpty = binding.editTextTextPostalAddress.text.isNotBlank()

        binding.nextButton.isEnabled = isPlaceNameNotEmpty && isPostalAddressNotEmpty
    }
}