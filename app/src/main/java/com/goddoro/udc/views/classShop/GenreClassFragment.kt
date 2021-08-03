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
import com.goddoro.common.data.model.Genre
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.Navigator
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentGenreClassBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.core.parameter.parametersOf

class GenreClassFragment : Fragment() {

    private val TAG = GenreClassFragment::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var binding : FragmentGenreClassBinding

    private lateinit var  viewModel : GenreClassViewModel

    private val navigator : Navigator by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentGenreClassBinding.inflate(inflater,container,false).also { binding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = getViewModel {
            parametersOf(arguments?.getParcelable(ARG_GENRE))
        }

        binding.vm = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        debugE(TAG, "GOOOD!!!!")

        setupRecyclerView()


    }

    private fun setupRecyclerView() {

        binding.recyclerView.apply {
            val mVideoGridLayoutManager: LinearLayoutManager = GridLayoutManager(context, 3)
            val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
            val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

            val mVideoGridSpacing =
                GridSpacingItemDecoration(3, spacingLeft, spacingTop, 0)

            layoutManager = mVideoGridLayoutManager
            addItemDecoration(mVideoGridSpacing)
            setHasFixedSize(true)

            adapter = NormalClassAdapter().apply {

                clickEvent.subscribe{
                    navigator.startClassDetailActivity(requireActivity(),it.first,it.second)
                }.disposedBy(compositeDisposable)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        private const val ARG_GENRE = "ARG_GENRE"

        fun newInstance( genre : Genre?) : GenreClassFragment {

            val fragment = GenreClassFragment()
            val bundle = Bundle()
            bundle.putParcelable(ARG_GENRE,genre)
            fragment.arguments = bundle

            return fragment
        }
    }
}