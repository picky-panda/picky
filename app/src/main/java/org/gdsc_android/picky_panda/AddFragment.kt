package org.gdsc_android.picky_panda

import android.os.Bundle
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

        // MyPageFragment에서 ImageButton 클릭 시 SettingFragment로 이동
        binding.nextButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(AddDetailFragment())
        }
    }
}