package com.goddoro.udc.views.profile.join

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.goddoro.common.common.debugE
import com.goddoro.udc.databinding.FragmentJoinEventBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.udc.R
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * created By DORO 2020/09/12
 */

class JoinEventFragment : Fragment() {

    private val TAG = JoinEventFragment::class.java.simpleName

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentJoinEventBinding

    /**
     * ViewModel Instance
     */



    private val mViewModel: JoinEventViewModel by viewModel()


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

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {
            val mVideoGridLayoutManager: LinearLayoutManager = GridLayoutManager(context, 3)
            val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
            val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

            val mVideoGridSpacing =
                GridSpacingItemDecoration(3, spacingLeft, spacingTop, 0)

            layoutManager = mVideoGridLayoutManager
            addItemDecoration(mVideoGridSpacing)
            setHasFixedSize(true)

            adapter = JoinEventAdapter()

        }
    }

    companion object {
        fun newInstance() = JoinEventFragment()
    }
}