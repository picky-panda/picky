package org.gdsc_android.picky_panda

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch
import org.gdsc_android.picky_panda.MyPageFragment
import org.gdsc_android.picky_panda.adapter.RecentlyEvaluatedAdapter
import org.gdsc_android.picky_panda.data.ResponseMyStoreListData
import org.gdsc_android.picky_panda.data.ServiceApi
import org.gdsc_android.picky_panda.databinding.FragmentMyPageBinding
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MyPageFragment : Fragment() {
    private lateinit var binding: FragmentMyPageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Coroutine
        viewLifecycleOwner.lifecycleScope.launch {
            // Get userProfile
            val userProfile = getUserProfile()

            // profile info
            val profileImageView = view.findViewById<ImageView>(R.id.profilePicImageView)
            val idTextView = view.findViewById<TextView>(R.id.idTextView)
            val descriptionCountTextView = view.findViewById<TextView>(R.id.descriptionCountTextView)
            val reviewCountTextView = view.findViewById<TextView>(R.id.reviewCountTextView)
            val savedRestaurantCountTextView = view.findViewById<TextView>(R.id.savedRestaurantCountTextView)

            idTextView.text = userProfile?.data?.email
            descriptionCountTextView.text = userProfile?.data?.myDescriptionCount.toString()
            reviewCountTextView.text = userProfile?.data?.myReviewCount.toString()
            savedRestaurantCountTextView.text = userProfile?.data?.mySavedRestaurantCount.toString()


            // recycler view setting
            val recyclerView = view.findViewById<RecyclerView>(R.id.RecentlyEvaluatedRecyclerView)
            val adapter = RecentlyEvaluatedAdapter(respnse.data?.recentlyEvaluatedList ?: emptyList())
            recyclerView.adapter = adapter
        }

        // move to SettingFragment
        binding.settingButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(SettingFragment())
        }
        // move to RegisterFragment
        binding.registerButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(RegisterFragment())
        }
        //// move to MyReviewFragment
        binding.reviewButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(MyReviewFragment())
        }
        //// move to SavedFragment
        binding.savedButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(SavedFragment())
        }
        //// move to RecentlyEvaluatedFragment
        binding.moreButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(RecentlyEvaluatedFragment())
        }

    }
    suspend fun getUserProfile(): ResponseMyStoreListData? {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://34.64.159.113:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ServiceApi::class.java)

        return try {
            val sharedPreferences = requireActivity().getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val accessToken = sharedPreferences.getString("accessToken", "")
            val response = service.myStoreList("Bearer $accessToken").execute()

            if (response.isSuccessful) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            null
        }
    }



}
