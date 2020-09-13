package com.goddoro.udc.views.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.goddoro.udc.databinding.FragmentHomeBinding
import com.goddoro.udc.databinding.FragmentVideoListBinding
import com.goddoro.udc.views.home.HomeFragment
import com.goddoro.udc.views.home.HomeViewModel
import dagger.android.support.DaggerFragment


/**
 * created By DORO 2020/08/16
 */

class VideoListFragment : DaggerFragment() {

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentVideoListBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: VideoListViewModel by lazy {
        ViewModelProvider(requireActivity())[VideoListViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = FragmentVideoListBinding.inflate(inflater, container, false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel


        observeViewModel()

    }

    private fun observeViewModel () {

        mViewModel.apply {


        }
    }

    companion object {
        fun newInstance () = VideoListFragment()
    }
}