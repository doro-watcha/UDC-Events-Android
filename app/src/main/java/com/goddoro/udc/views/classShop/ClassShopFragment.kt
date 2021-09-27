package com.goddoro.udc.views.classShop

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.goddoro.common.Broadcast
import com.goddoro.common.CommonConst.AUTO_SCROLL_SPEED
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.extension.rxRepeatTimer
import com.goddoro.common.extension.rxSingleTimer
import com.goddoro.common.util.CommonUtils
import com.goddoro.common.util.Navigator
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentClassShopBinding
import com.goddoro.udc.util.setCurrentItem
import com.goddoro.udc.util.startActivity
import com.goddoro.udc.views.classShop.detail.ClassDetailActivity
import com.goddoro.udc.views.search.SearchActivity
import com.goddoro.udc.views.udc.UdcViewModel_Factory.create
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Thread.sleep
import java.net.URI.create


/**
 * created By DORO 2020/10/24
 */

class ClassShopFragment : Fragment() {

    private val TAG = ClassShopFragment::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding: FragmentClassShopBinding

    private val mViewModel: ClassShopViewModel by viewModel()

    private val navigator: Navigator by inject()

    private val autoScrollDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        FragmentClassShopBinding.inflate(inflater, container, false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        observeViewModel()

        setupDateRecyclerView()
        setupDayOfRecyclerView()


    }


    private fun setupViewPager() {

        mBinding.mainViewPager.apply {

            adapter = MainClassAdapter().apply {

                clickEvent.subscribe {

                    navigator.startClassDetailActivity(requireActivity(), it.first.id, it.second)
                }.disposedBy(compositeDisposable)


            }

            var centerValue = Integer.MAX_VALUE / 2

            val findFirstPosition = centerValue % (mViewModel.mainClasses.value?.size ?: 1)

            centerValue -= findFirstPosition

            setCurrentItem(centerValue, false)

            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> {

                            scrollToNext()
                            val position = mBinding.mainViewPager.currentItem
                            mBinding.indicator.refresh(
                                position % (mViewModel.mainClasses.value?.size ?: 1)
                            )


                        }

                    }
                }
            })

            scrollToNext()
        }
    }

    private fun setupDateRecyclerView() {

        mBinding.dateRecyclerView.apply {

            val mVideoGridLayoutManager: LinearLayoutManager = GridLayoutManager(context, 7)
            val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
            val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

            val mVideoGridSpacing =
                GridSpacingItemDecoration(7, spacingLeft, spacingTop, 0)

            layoutManager = mVideoGridLayoutManager
            addItemDecoration(mVideoGridSpacing)
            setHasFixedSize(true)

            adapter = DateListAdapter(context).apply {

                clickEvent.subscribe {
                    mViewModel.listDateClasses(it)
                }.disposedBy(compositeDisposable)
            }
        }
    }

    private fun setupDayOfRecyclerView() {

        mBinding.dayOfClassRecyclerView.apply {

            adapter = DayOfClassAdapter().apply {

                clickEvent.subscribe{
                    navigator.startClassDetailActivity(requireActivity(), it.first.id, it.second)
                }.disposedBy(compositeDisposable)
            }
        }
    }

    private fun setupViewPagerWithTab() {

        mBinding.apply {

            var position = 0
            mViewModel.genres.value?.forEach {
                genreTabLayout.addTab(genreTabLayout.newTab().setText(it.name), position++)
            }


            /**
             * 2. viewpagerAdapter 생성 / viewPager.adapter 로 설정
             */
            val viewPagerAdapter = GenreClassPagerAdapter(childFragmentManager)
            genreViewPager.adapter = viewPagerAdapter


            TabLayoutMediator(genreTabLayout, genreViewPager) { tab, position ->
                tab.text = mViewModel.genres.value?.get(position)?.name
            }.attach()

            CommonUtils.reduceMarginsInTabs(genreTabLayout, 30)
            genreViewPager.offscreenPageLimit = 1


        }
    }


    private fun observeViewModel() {

        mViewModel.apply {

            genres.observe(viewLifecycleOwner, Observer {
                if (it != null) {
                    setupViewPagerWithTab()
                }
            })

            mainClasses.observe(viewLifecycleOwner, Observer {
                if ( it?.size ?: 0 > 0) {
                    setupViewPager()
                }
            })

            clickSearchClass.observeOnce(viewLifecycleOwner){
                startActivity(SearchActivity::class)
            }

            errorInvoked.observeOnce(viewLifecycleOwner) {
                debugE(TAG, it.message)
            }
        }

    }

    private fun scrollToNext () {

        if ( mViewModel.mainClasses.value?.size ?: 0 > 0 ) {

            autoScrollDisposable.clear()
            rxSingleTimer(AUTO_SCROLL_SPEED) {


                val position = mBinding.mainViewPager.currentItem + 1

                debugE(TAG, position.toString())

                mBinding.indicator.refresh(position % (mViewModel.mainClasses.value?.size ?: 1))
                mBinding.mainViewPager.setCurrentItem(position, 600)

            }.disposedBy(autoScrollDisposable)
        }

    }


    inner class GenreClassPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStateAdapter(fragmentManager, lifecycle) {

        override fun getItemCount(): Int = mViewModel.genres.value?.size ?: 0

        override fun createFragment(position: Int): Fragment {
            return GenreClassFragment.newInstance(mViewModel.genres.value?.get(position))
        }
    }
    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        debugE(TAG, "hidden " + hidden )

        if (hidden) {
            autoScrollDisposable.clear()
        }
        else {
            scrollToNext()
        }
    }

    override fun onResume() {
        super.onResume()

        debugE(TAG, "onResume")

        scrollToNext()
    }

    override fun onPause() {
        super.onPause()

        debugE(TAG, "onPause")


        autoScrollDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()

        debugE(TAG, "onDestroy")

        autoScrollDisposable.dispose()

        compositeDisposable.clear()
    }


    companion object {

        fun newInstance() = ClassShopFragment()
    }
}