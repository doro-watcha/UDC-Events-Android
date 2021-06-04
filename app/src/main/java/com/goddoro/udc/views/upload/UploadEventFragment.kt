package com.goddoro.udc.views.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import com.goddoro.common.Broadcast
import com.goddoro.common.Broadcast.eventUploadBroadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.dialog.showCommonDialog
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.Navigator
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentUploadEventBinding
import com.goddoro.udc.views.upload.calendar.CalendarDialog
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 2/16/21
 */

class UploadEventFragment : Fragment() {

    private val TAG = UploadEventFragment::class.java.simpleName

    private val navigator : Navigator by inject()

    private lateinit var mBinding : FragmentUploadEventBinding
    private val mViewModel : UploadEventViewModel by sharedViewModel()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentUploadEventBinding.inflate(inflater,container,false).also { mBinding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()

        setupBroadcast()
    }

    private fun observeViewModel() {
        mViewModel.apply {

            curPoster.observe(viewLifecycleOwner){

            }

            clickPickPoster.observeOnce(viewLifecycleOwner){
                TedImagePicker.with(requireContext())

                    .title(resources.getString(R.string.txt_pick_poster))
                    .showCameraTile(false)
                    .mediaType(
                        MediaType.IMAGE
                    )
                    .start {
                        curPoster.value = it
                    }
            }

            clickPickImage.observeOnce(viewLifecycleOwner){
                TedImagePicker.with(requireContext())
                    .title(resources.getString(R.string.txt_pick_image))
                    .showCameraTile(false)
                    .selectedUri(eventDetailImages.value)
                    .mediaType(
                        MediaType.IMAGE
                    )
                    .startMultiImage {
                        eventDetailImages.value = it
                    }

            }

            clickTypeDialog.observeOnce(viewLifecycleOwner){
                val dialog = EventTypeDialog(object : EventTypeDialog.onClickTypeListener {
                    override fun onClickType(type: String) {
                        mViewModel.type.value = type
                    }
                })
                dialog.show(requireActivity().supportFragmentManager,dialog.tag)
            }

            clickCalendarDialog.observeOnce(viewLifecycleOwner){
                val dialog = CalendarDialog(object: CalendarDialog.onClickDateListener{
                    override fun onClickDate(date: String) {
                        mViewModel.date.value = date
                    }

                })
                dialog.show(requireActivity().supportFragmentManager,dialog.tag)
            }
            clickUploadButton.observeOnce(viewLifecycleOwner) {
                Broadcast.eventUploadBroadcast.onNext(Unit)
                requireActivity().finish()
            }

            clickBackArrow.observeOnce(viewLifecycleOwner){
                requireActivity().finish()
            }

            clickSearchAddress.observeOnce(viewLifecycleOwner){
                navigator.startSearchAddressActivity(requireActivity())
            }
            uploadCompleted.observeOnce(viewLifecycleOwner){
                eventUploadBroadcast.onNext(Unit)
                requireActivity().finish()
            }
            errorInvoked.observeOnce(viewLifecycleOwner){
                debugE(TAG, it.message)
                showCommonDialog(R.string.dialog_error_unknown)
            }


        }


    }

    private fun setupBroadcast() {


        Broadcast.apply {


            findAddressBroadcast.subscribe{
                mViewModel.location.value = it
            }.disposedBy(compositeDisposable)
        }
    }


    companion object {
        fun newInstance() = UploadEventFragment()
    }

}