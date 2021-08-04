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
import com.goddoro.map.EventMapFragment
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivitySearchBinding
import com.goddoro.udc.views.classShop.ClassShopFragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.goddoro.common.Broadcast
import com.goddoro.common.common.navigation.MainMenu


class SearchActivity : AppCompatActivity() {

    private val TAG = SearchActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding : ActivitySearchBinding
    private val mViewModel : SearchViewModel by viewModel()

    private val navigator : Navigator by inject()

    private val queryChanged: BehaviorSubject<String> = BehaviorSubject.create()

    private val fragment1 = SearchFragment.newInstance()
    private val fragment2 = SearchResultFragment.newInstance()
    private var curFragment : Fragment = fragment1
    private var curIndex = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivitySearchBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        initView()
        observeViewModel()

        initFragments(savedInstanceState == null)
    }

    private fun initFragments(isFirstCreation : Boolean) {

        if(isFirstCreation) {
            supportFragmentManager.beginTransaction().add(R.id.search_container, fragment2, "2").hide(fragment2).commit()
            supportFragmentManager.beginTransaction().add(R.id.search_container, fragment1, "3").commit()
        }
    }

    private fun changeFragment( index : Int ) {

        if ( curIndex == index ) return

        val willShow = when (index) {
            1 -> fragment1
            2 -> fragment2
            else -> throw Error()
        }
        supportFragmentManager.beginTransaction().hide(curFragment).show(willShow).commit()
        curFragment = willShow
        curIndex = index
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


    private fun observeViewModel() {

        mViewModel.apply {

            query.observe(this@SearchActivity, Observer {

                if (it.isEmpty()) {
                    changeFragment(1)
                } else {
                    changeFragment(2)
                }
            })

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