package com.goddoro.udc.views.event

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.goddoro.udc.databinding.FragmentEventBinding
import com.goddoro.udc.views.home.HomeViewModel
import dagger.android.support.DaggerFragment
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 2020/08/16
 */

class EventFragment : Fragment() {


    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentEventBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: EventViewModel by sharedViewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) = FragmentEventBinding.inflate(inflater, container, false).also { mBinding = it }.root

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
        fun newInstance () = EventFragment()
    }
}