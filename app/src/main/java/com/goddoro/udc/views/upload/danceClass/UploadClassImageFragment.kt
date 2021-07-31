package com.goddoro.udc.views.upload.danceClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.udc.databinding.FragmentUploadClassImageBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UploadClassImageFragment : Fragment() {

    private lateinit var binding : FragmentUploadClassImageBinding
    private val viewModel : UploadClassViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUploadClassImageBinding.inflate ( inflater, container, false).also { binding = it}.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    companion object {

        fun newInstance() = UploadClassImageFragment()
    }
}