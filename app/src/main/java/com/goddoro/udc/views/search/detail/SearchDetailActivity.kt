package com.goddoro.udc.views.search.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.goddoro.common.common.observeOnce
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.Navigator
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivitySearchDetailBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class SearchDetailActivity : AppCompatActivity() {

    private val TAG = SearchDetailActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding : ActivitySearchDetailBinding
    private lateinit var  mViewModel : SearchDetailViewModel

    private val navigator : Navigator by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchDetailBinding.inflate(LayoutInflater.from(this))

        mViewModel = getViewModel {
            parametersOf(
                intent.getStringExtra(ARG_QUERY)
            )
        }

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        setupRecyclerView()
        observeViewModel()
    }

    private fun initView() {

        mBinding.apply {

            txtEventQuery.isSelected = true
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            clickBackArrow.observeOnce(this@SearchDetailActivity){
                onBackPressed()
            }

        }

    }

    private fun setupRecyclerView() {

        val mVideoGridLayoutManager: LinearLayoutManager = GridLayoutManager(this, 2)
        val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
        val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

        val mVideoGridSpacing =
            GridSpacingItemDecoration(2, spacingLeft, spacingTop, 0)
        mBinding.mRecyclerView.apply {
            layoutManager = mVideoGridLayoutManager
            addItemDecoration(mVideoGridSpacing)
            setHasFixedSize(true)

            adapter = SearchEventAdapter().apply {

                clickEvent.subscribe{
                    //navigator.startEventDetailActivity(this@SearchDetailActivity,it.first.id,it.second)
                }.disposedBy(compositeDisposable)
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        private val ARG_QUERY = "ARG_QUERY"

        fun newIntent (activity : Activity, query : String) : Intent {

            val intent = Intent ( activity, SearchDetailActivity::class.java)

            intent.putExtra(ARG_QUERY,query)

            return intent
        }
    }
}