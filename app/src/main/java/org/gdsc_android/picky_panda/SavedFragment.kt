package org.gdsc_android.picky_panda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.gdsc_android.picky_panda.SavedFragment
import org.gdsc_android.picky_panda.databinding.FragmentSavedBinding

class SavedFragment : Fragment() {

    private lateinit var binding: FragmentSavedBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // SavedFragment에서 ImageButton 클릭 시 MypageFragment로 돌아감
        binding.fromSavedToMyPageButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(MyPageFragment())
        }
    }
}