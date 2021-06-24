package com.goddoro.udc.views.profile.collection

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goddoro.common.common.debugE
import com.goddoro.udc.databinding.FragmentEventCollectionBinding
import androidx.fragment.app.Fragment
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.Navigator
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * created By DORO 2020/09/12
 */

class EventCollectionFragment : Fragment() {


    private val TAG = EventCollectionFragment::class.java.simpleName
    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentEventCollectionBinding

    /**
     * ViewModel Instance
     */

    private val mViewModel: EventCollectionViewModel by viewModel()

    private val navigator : Navigator by inject()

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentEventCollectionBinding.inflate(inflater,container,false).also{mBinding=it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        debugE(TAG, "EventCollectionFragment")

        setupRecyclerView()
        setupRefreshLayout()
        observeViewModel()
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

    private fun setupRefreshLayout() {

        mBinding.mSwipeRefreshLayout.setOnRefreshListener {
            mViewModel.refresh()
        }
    }
    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = EventCollectionAdapter().apply {


                clickEvent.subscribe{

                    navigator.startEventDetailActivity(requireActivity(),it.first,it.second)
                }.disposedBy(compositeDisposable)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        fun newInstance() = EventCollectionFragment()
    }
}