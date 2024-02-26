package org.gdsc_android.picky_panda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.gdsc_android.picky_panda.MyReviewFragment
import org.gdsc_android.picky_panda.databinding.FragmentMyReviewBinding

class MyReviewFragment : Fragment() {

    private lateinit var binding: FragmentMyReviewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // move to MypageFragment
        binding.fromMyReviewToMyPageButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(MyPageFragment())
        }
    }
}