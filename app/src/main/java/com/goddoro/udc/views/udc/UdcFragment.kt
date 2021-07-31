package com.goddoro.udc.views.udc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goddoro.udc.databinding.FragmentUdcBinding
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * created By DORO 2020/08/16
 */

class UdcFragment : Fragment() {

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentUdcBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: UdcViewModel by viewModel()

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