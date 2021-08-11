package com.goddoro.udc.views.profile.star

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.udc.databinding.FragmentMyStarBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyStarFragment : Fragment() {

    private lateinit var binding : FragmentMyStarBinding

    private val viewModel : MyStarViewModel by viewModel()

    private val starDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMyStarBinding.inflate(inflater, container,false).also { binding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        setupRecyclerView()
    }

    private fun setupRecyclerView() {

        binding.recyclerView.apply {

            adapter = StarClassAdapter()
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        starDisposable.dispose()
    }
}