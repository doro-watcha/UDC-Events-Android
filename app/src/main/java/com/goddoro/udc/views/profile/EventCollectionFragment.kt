package com.goddoro.udc.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.debugE
import com.goddoro.udc.databinding.FragmentEventCollectionBinding
import com.goddoro.udc.databinding.FragmentHomeBinding
import com.goddoro.udc.views.home.HomeFragment
import com.goddoro.udc.views.home.HomeViewModel
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class EventCollectionFragment : DaggerFragment(), HasDefaultViewModelProviderFactory {


    private val TAG = EventCollectionFragment::class.java.simpleName
    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentEventCollectionBinding

    /**
     * ViewModel Instance
     */

    private val mViewModel: EventCollectionViewModel by lazy {
        ViewModelProvider(this)[EventCollectionViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentEventCollectionBinding.inflate(inflater,container,false).also{mBinding=it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        debugE(TAG, "EventCollectionFragment")

        setupRecyclerView()
        observeViewModel()
    }

    private fun observeViewModel() {



    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = EventCollectionAdapter()
        }
    }

    companion object {

        fun newInstance() = EventCollectionFragment()
    }
}