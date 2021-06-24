package com.goddoro.udc.views.profile.pending

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.common.Broadcast
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.Navigator
import com.goddoro.udc.databinding.FragmentEventPendingBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class PendingEventFragment : Fragment() {

    private lateinit var mBinding : FragmentEventPendingBinding

    private val mViewModel : PendingEventViewModel by viewModel()

    val eventUploadDisposable = CompositeDisposable()

    private val navigator : Navigator by inject()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEventPendingBinding.inflate(inflater,container,false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        setupRecyclerView()
        observeViewModel()
        setupRefreshLayout()
        setupBroadcast()
    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = PendingEventAdapter().apply {

                clickEvent.subscribe{

                    navigator.startEventDetailActivity(requireActivity(),it.first,it.second)
                }.disposedBy(compositeDisposable)
            }
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            onLoadCompleted.observe(viewLifecycleOwner){

                if ( it == true || mBinding.mSwipeRefreshLayout.isRefreshing ) {
                    mBinding.mSwipeRefreshLayout.isRefreshing = false
                }
            }

            errorInvoked.observe(viewLifecycleOwner){
                if ( mBinding.mSwipeRefreshLayout.isRefreshing) {
                    mBinding.mSwipeRefreshLayout.isRefreshing = false
                }
            }
        }


    }

    private fun setupBroadcast() {

        Broadcast.apply {

            eventUploadBroadcast.subscribe({
                mViewModel.refresh()
            },{
                mViewModel.errorInvoked.value = it
            }).disposedBy(eventUploadDisposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        eventUploadDisposable.clear()
    }

    private fun setupRefreshLayout() {

        mBinding.mSwipeRefreshLayout.setOnRefreshListener {
            mViewModel.refresh()
        }
    }

    companion object {
        fun newInstance() = PendingEventFragment()
    }
}