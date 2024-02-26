package org.gdsc_android.picky_panda

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import org.gdsc_android.picky_panda.databinding.FragmentDetailBinding
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private var isBookmarked = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // click event using view binding
        binding.addReviewButton.setOnClickListener{
            showBottomSheetDialog()
        }

        binding.bookmarkButton.setOnClickListener {
            // change the status of the button
            isBookmarked = !isBookmarked
            updateBookmarkButtonState()
            // other click events
        }
    }

    private fun updateBookmarkButtonState() {
            // change the background if clicked
            val backgroundResId = if (isBookmarked) {
                R.drawable.baseline_bookmark_24
            } else {
                R.drawable.baseline_bookmark_border_24
            }
            binding.bookmarkButton.setBackgroundResource(backgroundResId)

    }

    private fun showBottomSheetDialog() {
        val bottomSheetFragment = reviewBottomSheetFragment()
        bottomSheetFragment.show(parentFragmentManager, bottomSheetFragment.tag)

    }
}