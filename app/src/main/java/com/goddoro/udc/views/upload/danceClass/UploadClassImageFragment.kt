package com.goddoro.udc.views.upload.danceClass

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.goddoro.common.common.observeOnce
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.common.extension.disposedBy
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentUploadClassImageBinding
import com.goddoro.udc.views.upload.EventDetailImageAdapter
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class UploadClassImageFragment : Fragment() {

    private val TAG = UploadClassImageFragment::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

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
        observeViewModel()

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

            adapter = ClassDetailImageAdapter(context).apply {
                clickEvent.subscribe{
                    viewModel.mainIndex = it
                }.disposedBy(compositeDisposable)
            }

        }
    }

    private fun observeViewModel() {

        viewModel.apply {

            clickGalleryButton.observeOnce(viewLifecycleOwner) {
                TedImagePicker.with(requireContext())
                    .title(resources.getString(R.string.txt_pick_class_images))
                    .showCameraTile(false)
                    .max(10,"최대 10개까지 선택할 수 있습니다")
                    .selectedUri(detailImages.value)
                    .mediaType(
                        MediaType.IMAGE
                    )
                    .startMultiImage {
                        detailImages.value = it
                    }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        fun newInstance() = UploadClassImageFragment()
    }
}