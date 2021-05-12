package com.goddoro.udc.views.upload

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.udc.databinding.FragmentEventPreviewBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 2/16/21
 */

class EventPreviewFragment : Fragment() {


    private lateinit var mBinding : FragmentEventPreviewBinding

    private val mViewModel : UploadEventViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentEventPreviewBinding.inflate(inflater,container,false).also { mBinding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        initView()

    }

    private fun initView() {



    }


    companion object {
        fun newInstance() = EventPreviewFragment()
    }
}