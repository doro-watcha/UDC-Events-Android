package com.goddoro.udc.views.tag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.udc.databinding.FragmentTagArtistBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

/**
 * created By DORO 2020/10/17
 */


class TagArtistFragment : Fragment() {


    private lateinit var mBinding : FragmentTagArtistBinding

    private val mViewModel : TagDetailViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentTagArtistBinding.inflate(inflater,container,false).also{mBinding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        observeViewModel()
    }

    private fun observeViewModel(){


    }

    companion object {

        fun newInstance () = TagArtistFragment()
    }
}