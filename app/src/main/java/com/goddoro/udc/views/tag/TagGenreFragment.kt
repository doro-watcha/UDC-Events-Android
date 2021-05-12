package com.goddoro.udc.views.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.udc.databinding.FragmentTagGenreBinding
import com.goddoro.udc.views.tag.adapter.MyTagAdapter
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


/**
 * created By DORO 2020/10/17
 */

class TagGenreFragment : Fragment() {

    private lateinit var mBinding : FragmentTagGenreBinding

    private val mViewModel : TagDetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentTagGenreBinding.inflate(inflater,container,false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
        observeViewModel()
    }


    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = MyTagAdapter()
        }
    }
    private fun observeViewModel () {




    }


    companion object {
        fun newInstance () = TagGenreFragment()
    }
}