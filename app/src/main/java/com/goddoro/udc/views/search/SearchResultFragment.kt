package com.goddoro.udc.views.search
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.goddoro.udc.databinding.FragmentSearchBinding
import com.goddoro.udc.databinding.FragmentSearchResultBinding
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchResultFragment : Fragment() {

    private lateinit var binding: FragmentSearchResultBinding

    private val viewModel: SearchViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSearchResultBinding.inflate(inflater, container, false).also { binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

    }

    companion object {

        fun newInstance() = SearchResultFragment()
    }
}