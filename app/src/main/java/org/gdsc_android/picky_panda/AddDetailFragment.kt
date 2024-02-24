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
import androidx.core.view.WindowInsetsAnimationCompat
import androidx.core.view.children
import com.google.android.gms.common.api.Response
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.ResponseBody
import org.gdsc_android.picky_panda.AddDetailFragment
import org.gdsc_android.picky_panda.data.GeocodingResponse
import org.gdsc_android.picky_panda.data.GetGeocodingResult
import org.gdsc_android.picky_panda.data.RegisterData
import org.gdsc_android.picky_panda.data.RestaurantRepository
import org.gdsc_android.picky_panda.databinding.FragmentAddDetailBinding
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

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
            CoroutineScope(Dispatchers.Main).launch {
                if (validateInputs()) {
                    val address = binding.placeAddressFixedTextView.text.toString()
                    val geocodingResult = getGeocodingResult(address)
                    val latitude = geocodingResult.latitude
                    val longitude = geocodingResult.longtitude

                    // 입력된 정보를 바탕으로 RegisterData 객체(식당 정보)를 생성
                    val restaurant = RegisterData(
                        placeName = binding.placeNAmeFixedTextView.text.toString(),
                        address = address,
                        latitude = latitude,
                        longitude = longitude,
                        category = binding.placeCategorySpinner.selectedItem.toString(),
                        options = selectedChipIds.joinToString(","),
                        description = binding.whatKindOfFoodEditTextText.text.toString()
                    )

                    // RestaurantRepository의 addRestaurant 메서드를 호출하여 서버에 식당 정보를 전송
                    val call = RestaurantRepository.addRestaurant(restaurant)

                    // enqueue 메서드를 호출하여 서버에 비동기 요청 보냄
                    call.enqueue(object :Callback<ResponseBody> {
                        // 서버로부터 응답을 받으면 이 메서드가 호출
                        override fun onResponse(
                            call: retrofit2.Call<ResponseBody>,
                            response: retrofit2.Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                showToast("Submitted!")
                                (activity as MainActivity).replaceFragment(AddFragment())
                            }
                            else {
                                showToast("Failed to submit!")
                            }
                        }

                        override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                            showToast("Fail to connect!")
                        }
                    })


                }
            }

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
    private fun validateInputs(): Boolean {
        if (binding.placeCategorySpinner.selectedItem == null) {
            showToast("Please select a category.")
            return false
        }
        if (selectedChipIds.isEmpty()) {
            showToast("Please select at least one option.")
            return false
        }
        return true
    }
    suspend fun getGeocodingResult(address: String): GetGeocodingResult {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(GeocodingService::class.java)

        // 코루틴을 사용하여 비동기 호출을 수행합니다.
        val response = service.getGeocodeAsync(address, "GOOGLE_MAPS_API_KEY").await()
        val result = response.results.firstOrNull()

        val latitude = result?.geometry?.location?.lat ?: 0.0
        val longitude = result?.geometry?.location?.lng ?: 0.0

        return GetGeocodingResult(latitude, longitude)
    }

    interface GeocodingService {
        @GET("maps/api/geocode/json")
        // Call 객체 대신 Deferred 객체를 반환합니다.
        fun getGeocodeAsync(@Query("address") address: String, @Query("key") key: String): Deferred<GeocodingResponse>
    }

}