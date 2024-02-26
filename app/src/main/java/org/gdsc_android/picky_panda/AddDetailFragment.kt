package org.gdsc_android.picky_panda

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.google.android.material.chip.Chip
import kotlinx.coroutines.launch
import org.gdsc_android.picky_panda.data.CategoryClass
import org.gdsc_android.picky_panda.data.GetGeocodingResult
import org.gdsc_android.picky_panda.data.RequestRegisterStoreData
import org.gdsc_android.picky_panda.data.ResponseRegisterStoreData
import org.gdsc_android.picky_panda.data.ServiceApi
import org.gdsc_android.picky_panda.databinding.FragmentAddDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val placeName = arguments?.getString("placeName")
        val postalAddress = arguments?.getString("postalAddress")

        binding.placeNameFixedTextView.text = placeName
        binding.placeAddressFixedTextView.text = postalAddress

        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.placeCategory,
            android.R.layout.simple_spinner_item
        )

        binding.placeCategorySpinner.adapter = adapter
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val chipTexts = listOf("Vegan", "Gluten Free")

        chipTexts.forEach { text ->
            val chip = Chip(context)
            chip.text = text
            binding.optionChipGroup.addView(chip)
        }

        //Detect changes in the check status of each Chip and update selectedChipIds accordingly
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

        binding.placeCategorySpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    checkSubmitButtonEnable()
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    checkSubmitButtonEnable()
                }
            }
        var latitude: Double = 0.0
        var longitude: Double = 0.0
        val address = binding.placeAddressFixedTextView.text.toString()
        lifecycleScope.launch {
            val geocodingResult = try {
                getGeocodingResult(address)
            } catch (e: Exception) {
                showToast(e.message ?: "An error occurred.")
                return@launch
            }
            val latitude = geocodingResult.latitude
            val longitude = geocodingResult.longtitude
        }
        binding.submitButton.setOnClickListener {
            lifecycleScope.launch {
                if (validateInputs()) {
                    val placeName = binding.placeNameFixedTextView.toString()
                    val categoryString = binding.placeCategorySpinner.selectedItem.toString()
                    val category = CategoryClass.valueOf(categoryString)
                    val options = selectedChipIds.joinToString(", ") { id ->
                        binding.optionChipGroup.findViewById<Chip>(id).text.toString()
                    }
                    val description = binding.whatKindOfFoodEditTextText.toString()

                    //Send the store information received from the user to the server for storage
                    val requestRegisterStoreData = RequestRegisterStoreData(
                        placeName,
                        address,
                        latitude,
                        longitude,
                        category,
                        options,
                        description
                    )

                    val call = saveStoreDataToServer(requestRegisterStoreData)
                    call.enqueue(object : Callback<ResponseRegisterStoreData> {
                        override fun onResponse(call: Call<ResponseRegisterStoreData>, response: Response<ResponseRegisterStoreData>) {
                            if (response.isSuccessful) {
                                showToast("Submitted!")
                                (activity as MainActivity).replaceFragment(AddFragment())
                            } else {
                                showToast("Failed to submit")
                            }
                        }

                        override fun onFailure(call: Call<ResponseRegisterStoreData>, t: Throwable) {
                            showToast("Failed to submit due to network error")
                        }
                    })
                }
            }

        }

        binding.editButton.setOnClickListener {
            // move to AddFragment
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
    private fun validateInputs(): Boolean {
        if (selectedChipIds.isEmpty()) {
            showToast("Please select at least one option.")
            return false
        }
        return true
    }

    private suspend fun getGeocodingResult(address: String): GetGeocodingResult {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ServiceApi::class.java)

        // Use a coroutine to perform asynchronous calls
        val response = service.getGeocodeAsync(address, "GOOGLE_MAPS_API_KEY").await()
        val result = response.results.firstOrNull()

        if (result == null || result.geometry?.location == null) {
            throw Exception("No location found for the address.")
        }
        val latitude = result.geometry.location.lat
        val longitude = result.geometry.location.lng

        return GetGeocodingResult(latitude, longitude)
    }
    private suspend fun saveStoreDataToServer(data: RequestRegisterStoreData): Call<ResponseRegisterStoreData> {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.64.159.113:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ServiceApi::class.java)
        // SharedPreferences
        val sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val accessToken = sharedPreferences.getString("accessToken", "")

        return service.registerStore(data,"Bearer $accessToken")
    }
}