package com.goddoro.udc.views.udc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.goddoro.udc.databinding.FragmentHomeBinding
import com.goddoro.udc.databinding.FragmentUdcBinding
import com.goddoro.udc.views.home.HomeFragment
import com.goddoro.udc.views.home.HomeViewModel
import dagger.android.support.DaggerFragment


/**
 * created By DORO 2020/08/16
 */

class UdcFragment : DaggerFragment() {

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentUdcBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: UdcViewModel by lazy {
        ViewModelProvider(requireActivity())[UdcViewModel::class.java]
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = FragmentUdcBinding.inflate(inflater, container, false).also { mBinding = it }.root

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
        fun newInstance () = UdcFragment()
    }
}