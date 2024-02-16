package org.gdsc_android.picky_panda

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.gdsc_android.picky_panda.MyPageFragment
import org.gdsc_android.picky_panda.databinding.FragmentMyPageBinding

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

        // MyPageFragment에서 ImageButton 클릭 시 SettingFragment로 이동
        binding.settingButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(SettingFragment())
        }
        //// MyPageFragment에서 Button 클릭 시 RegisterFragment로 이동
        binding.registerButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(RegisterFragment())
        }
        //// MyPageFragment에서 Button 클릭 시 MyReviewFragment로 이동
        binding.reviewButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(MyReviewFragment())
        }
        //// MyPageFragment에서 Button 클릭 시 SavedFragment로 이동
        binding.savedButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(SavedFragment())
        }
        //// MyPageFragment에서 Button 클릭 시 RecentlyEvaluatedFragment로 이동
        binding.moreButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(RecentlyEvaluatedFragment())
        }

    }
}
