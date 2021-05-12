package com.goddoro.udc.views.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.observe
import com.goddoro.common.common.observeOnce
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.util.Navigator
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivitySearchBinding
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchActivity : AppCompatActivity() {

    private val TAG = SearchActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding : ActivitySearchBinding
    private val mViewModel : SearchViewModel by viewModel()

    private val navigator : Navigator by inject()

    private val queryChanged: BehaviorSubject<String> = BehaviorSubject.create()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        initView()
        setupRecyclerView()
        observeViewModel()
    }

    private fun initView() {

        mBinding.editQuery.setOnEditorActionListener { v, actionId, event ->
            when (actionId) {
                EditorInfo.IME_ACTION_SEARCH -> {
                    goResult(mViewModel.query.value ?: "")
                }
            }
            true
        }
    }

    private fun setupRecyclerView () {

        mBinding.apply {

            historyRecyclerView.let {

                it.adapter = SearchHistoryAdapter().apply {

                    clickEvent.subscribe{
                        goResult(it)
                    }.disposedBy(compositeDisposable)
                }
            }


            recommendationRecyclerView.let {

                it.adapter = SearchRecommendAdapter().apply {

                    clickEvent.subscribe{
                        goResult(it)
                    }.disposedBy(compositeDisposable)
                }

            }
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            query.observe(this@SearchActivity){

            }

            clickBackArrow.observeOnce(this@SearchActivity){
                onBackPressed()
            }
            clickCurrentQuery.observeOnce(this@SearchActivity){
                goResult(query.value ?: "")
            }
        }


    }

    private fun goResult ( query : String) {
        navigator.startSearchDetailActivity(this@SearchActivity,query)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.fade_out, R.anim.fade_out)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {

        fun newIntent(context: Activity) : Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }
}