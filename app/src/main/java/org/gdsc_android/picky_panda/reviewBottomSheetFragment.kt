package org.gdsc_android.picky_panda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.gdsc_android.picky_panda.databinding.FragmentHomeBinding
import org.gdsc_android.picky_panda.databinding.FragmentReviewBottomSheetBinding


class reviewBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentReviewBottomSheetBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReviewBottomSheetBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the contents of the Bottom Sheet dialog and handle events
        val reviewEditText = view.findViewById<EditText>(R.id.reviewEditText)
        val submitButton = view.findViewById<Button>(R.id.submitButton)
        val closeButton = view.findViewById<Button>(R.id.closeButton)

        submitButton.setOnClickListener {
            // Click the Submit Review button to handle the event
            val reviewText = reviewEditText.text.toString()
            // can write the review here
            // performing tasks such as saving, network transfer, etc
            dismiss()
        }

        closeButton.setOnClickListener {
            // Close button click event processing
            dismiss()
        }
    }


    companion object {

    }
}