package com.goddoro.udc.views.classShop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentNormalClassListBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 2020/10/24
 */


class NormalClassListFragment : Fragment() {

    private val TAG = NormalClassListFragment::class.java.simpleName


    private lateinit var mBinding: FragmentNormalClassListBinding

    private val mViewModel: ClassShopViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentNormalClassListBinding.inflate(inflater, container, false)
        .also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
        setupRecyclerView()

        debugE(TAG, "NOrmalClassListFragment")

        debugE(TAG, mViewModel.normalClasses.value)
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

            adapter = NormalClassAdapter()

        }
    }

    private fun observeViewModel() {

        mViewModel.apply {


        }
    }

    companion object {
        fun newInstance() = NormalClassListFragment()
    }
}