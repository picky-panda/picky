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
    private var isBookmarked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStoreDetailsBinding.inflate(layoutInflater, container, false)

        //Configure the UI to display details


        //Click the button to go to the detail screen
        binding.gotoDetailButton.setOnClickListener {
            (activity as MainActivity).replaceFragment(DetailFragment())
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookmarkButton.setOnClickListener {
            // Change the status of the button
            isBookmarked = !isBookmarked
            updateBookmarkButtonState()
        }
    }

    private fun updateBookmarkButtonState() {
        // Change the background according to the status of the button
        val backgroundResId = if (isBookmarked) {
            R.drawable.baseline_bookmark_24
        } else {
            R.drawable.baseline_bookmark_border_24
        }
        binding.bookmarkButton.setBackgroundResource(backgroundResId)
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