package org.gdsc_android.picky_panda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding
import org.gdsc_android.picky_panda.databinding.FragmentStoreDetailsBinding

class StoreDetailsFragment : Fragment() {

    private var _binding: FragmentStoreDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreDetailsBinding.inflate(layoutInflater, container, false)

        //상세정보를 표시하는 UI를 구성


        return binding.root
    }

    companion object {
        private const val ARG_STORE = "store"

        fun newInstance(storeName: String): StoreDetailsFragment {
            val fragment = StoreDetailsFragment()
            val args = Bundle()
            args.putString(ARG_STORE, storeName)
            fragment.arguments = args
            return fragment
        }

    }
}