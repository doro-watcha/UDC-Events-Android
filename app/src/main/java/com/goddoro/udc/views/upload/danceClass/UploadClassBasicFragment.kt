package com.goddoro.udc.views.upload.danceClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.common.common.StrPatternChecker.YoutubeUrlTypeOk
import com.goddoro.common.common.StrPatternChecker.getYoutubeIdFromUrl
import com.goddoro.common.common.debugE
import com.goddoro.udc.databinding.FragmentUploadClassBasicBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import java.util.regex.Pattern

class UploadClassBasicFragment : Fragment() {

    private val TAG = UploadClassBasicFragment::class.java.simpleName

    private lateinit var binding : FragmentUploadClassBasicBinding
    private val viewModel : UploadClassViewModel by sharedViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentUploadClassBasicBinding.inflate(inflater, container, false).also { binding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.apply {

            youtubeUrl.observe(viewLifecycleOwner){
                debugE(TAG, it)
                if ( YoutubeUrlTypeOk(it)) {

                    //binding.youtubeView.play(getYoutubeIdFromUrl(it))
                    debugE(TAG, getYoutubeIdFromUrl(it))

                }
            }

        }
    }



    companion object {

        fun newInstance() = UploadClassBasicFragment()
    }
}