package com.goddoro.udc.views.upload.danceClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.common.extension.disposedBy
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentUploadClassImageBinding
import com.goddoro.udc.views.classShop.NormalClassAdapter
import com.goddoro.udc.views.upload.EventDetailImageAdapter
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

        setupRecyclerView()

    }

    private fun setupRecyclerView() {

        binding.recyclerViewImages.apply {

            val mVideoGridLayoutManager: LinearLayoutManager = GridLayoutManager(context, 3)
            val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
            val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

            val mVideoGridSpacing =
                GridSpacingItemDecoration(3, spacingLeft, spacingTop, 0)

            layoutManager = mVideoGridLayoutManager
            addItemDecoration(mVideoGridSpacing)
            setHasFixedSize(true)

            adapter = EventDetailImageAdapter().apply {

            }

        }
    }

    companion object {

        fun newInstance() = UploadClassImageFragment()
    }
}