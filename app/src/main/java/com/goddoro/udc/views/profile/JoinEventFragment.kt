package com.goddoro.udc.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.debugE
import com.goddoro.udc.databinding.FragmentJoinEventBinding
import com.goddoro.udc.databinding.FragmentProfileBinding
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject


/**
 * created By DORO 2020/09/12
 */

class JoinEventFragment : DaggerFragment(), HasDefaultViewModelProviderFactory {

    private val TAG = JoinEventFragment::class.java.simpleName

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentJoinEventBinding

    /**
     * ViewModel Instance
     */



    private val mViewModel: JoinEventViewModel by lazy {
        ViewModelProvider(this)[JoinEventViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentJoinEventBinding.inflate(inflater,container,false).also{mBinding=it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()

        debugE(TAG," JoinEventFragment")
    }

    private fun setupRecyclerView () {

        mBinding.mRecyclerView.apply {

            adapter = JoinEventAdapter()
        }
    }

    companion object {
        fun newInstance() = JoinEventFragment()
    }
}