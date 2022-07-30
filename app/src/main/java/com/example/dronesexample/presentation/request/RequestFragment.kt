package com.example.dronesexample.presentation.request

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.dronesexample.R
import com.example.dronesexample.databinding.RequestFragmentBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class RequestFragment: BottomSheetDialogFragment() {

    private val binding: RequestFragmentBinding by lazy { initBinding() }
    private val requestViewModel: RequestViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.request_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requestViewModel.responseModel.observe(viewLifecycleOwner) {
            binding.tvResponseTitle.text = "Title: ${it.title}"
            binding.tvResponseBody.text = "Body: ${it.body}"
        }

        requestViewModel.responseError.observe(viewLifecycleOwner) { isResponseError ->
            if (isResponseError) {
                dialog?.window?.decorView?.rootView?.let {
                    Snackbar.make(it , "Ошибка!", Snackbar.LENGTH_SHORT).show()
                }
            }
        }

        binding.makeRequest.setOnClickListener { requestViewModel.makeRequest() }
    }

    private fun initBinding() = RequestFragmentBinding.bind(requireView())
}