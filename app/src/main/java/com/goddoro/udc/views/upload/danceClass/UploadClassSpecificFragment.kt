package com.goddoro.udc.views.upload.danceClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.common.common.observeOnce
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentUploadClassSpecificBinding
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UploadClassSpecificFragment : Fragment() {

    private lateinit var binding : FragmentUploadClassSpecificBinding

    private val viewModel : UploadClassViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUploadClassSpecificBinding.inflate (inflater, container, false).also { binding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.apply {

            clickArtistImage.observeOnce(viewLifecycleOwner) {

                TedImagePicker.with(requireContext())
                    .title(resources.getString(R.string.txt_input_artist_profile_msg))
                    .showCameraTile(false)
                    .mediaType(
                        MediaType.IMAGE
                    )
                    .start {
                        artistProfileImg.value = it
                    }
            }
        }
    }

    companion object {

        fun newInstance() = UploadClassSpecificFragment()
    }
}