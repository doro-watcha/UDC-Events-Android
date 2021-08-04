package com.goddoro.udc.views.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.common.extension.disposedBy
import com.goddoro.udc.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    private fun setupRecyclerView () {
//
//        binding.apply {
//
//            historyRecyclerView.let {
//
//                it.adapter = SearchHistoryAdapter().apply {
//
//                    clickEvent.subscribe{
//                        goResult(it)
//                    }.disposedBy(compositeDisposable)
//                }
//            }
//
//
//            recommendationRecyclerView.let {
//
//                it.adapter = SearchRecommendAdapter().apply {
//
//                    clickEvent.subscribe{
//                        goResult(it)
//                    }.disposedBy(compositeDisposable)
//                }
//
//            }
//        }
    }


    companion object {
        fun newInstance() = SearchFragment()
    }

}