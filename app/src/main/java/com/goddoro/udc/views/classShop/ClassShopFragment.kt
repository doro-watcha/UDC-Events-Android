package com.goddoro.udc.views.classShop

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.observe
import androidx.viewpager2.widget.ViewPager2
import com.goddoro.common.Broadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.extension.rxRepeatTimer
import com.goddoro.common.util.AppPreference_Factory.create
import com.goddoro.common.util.CommonUtils
import com.goddoro.common.util.Navigator
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentClassShopBinding
import com.goddoro.udc.views.classShop.detail.ClassDetailActivity
import com.goddoro.udc.views.udc.UdcViewModel_Factory.create
import com.google.android.material.tabs.TabLayout
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

    private lateinit var mBinding : FragmentClassShopBinding

    private val mViewModel : ClassShopViewModel by viewModel()

    private val autoScrollDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentClassShopBinding.inflate(inflater, container, false).also { mBinding = it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        observeViewModel()

        setupViewPagerWithTab()
        setupViewPager()
        startAutoScroll()
    }


    private fun setupViewPager() {

        mBinding.mMainClassViewPager.apply {

            adapter = MainClassAdapter().apply {

                clickEvent.subscribe{

                    val intent = ClassDetailActivity.newIntent(requireActivity(),it.first)

                    val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(),
                        it.second, ViewCompat.getTransitionName(it.second)!!
                    )

                    startActivity(intent,optionsCompat.toBundle())

                }.disposedBy(compositeDisposable)



            }

            var centerValue =  Integer.MAX_VALUE / 2

            val findFirstPosition = centerValue % ( mViewModel.mainClasses.value?.size ?: 1)

            centerValue -= findFirstPosition


            debugE(TAG, centerValue.toString())
            debugE(TAG, findFirstPosition.toString())

            setCurrentItem(centerValue - 2, false)
            debugE(TAG, "CURRENT ITEM IN SET UP $currentItem")
            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

                    if ( ( mViewModel.mainClasses.value?.size ?: 0 )> 0 )
                    mBinding.indicator.selection = position % (mViewModel.mainClasses.value?.size ?: 0)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })

        }
    }


    private fun setupViewPagerWithTab() {

        mBinding.apply {

            mTabLayout.addTab(
                mTabLayout.newTab().setText(getString(R.string.common_dance_class)),
                0
            )
            mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.common_workshop)), 1)

            /**
             * 2. viewpagerAdapter 생성 / viewPager.adapter 로 설정
             */
            val viewPagerAdapter = ClassShopPagerAdapter(childFragmentManager)
            mViewPager.adapter = viewPagerAdapter

            mTabLayout.setupWithViewPager(mViewPager)

            mViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(mTabLayout))
            mTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    mViewPager.currentItem = tab.position
                }

                override fun onTabUnselected(tab: TabLayout.Tab) {
                }

                override fun onTabReselected(tab: TabLayout.Tab) {
                }
            })
            /*
* Tab indicator margin조정
* default는 fullwidth이다.
 */
            CommonUtils.reduceMarginsInTabs(mTabLayout, 30)
            mViewPager.offscreenPageLimit = 3
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            mainClasses.observe(viewLifecycleOwner){

                mBinding.indicator.count = it?.size ?: 0
            }

            errorInvoked.observeOnce(viewLifecycleOwner){
                debugE(TAG, it.message)
            }
        }

    }

    private fun startAutoScroll () {

        rxRepeatTimer(5000){
            mBinding.mMainClassViewPager.apply {

                setCurrentItem(currentItem + 1 , true)
                debugE(TAG, "CURRENT ITEM IN AUTO SCROLL $currentItem")
            }

        }.disposedBy(autoScrollDisposable)


    }




    inner class ClassShopPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> NormalClassListFragment.newInstance()
                1 -> WorkShopListFragment.newInstance()
                else -> throw Exception("error")
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> resources.getString(R.string.common_dance_class)
                else -> resources.getString(R.string.common_workshop)
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()

        autoScrollDisposable.clear()
        compositeDisposable.clear()
    }



    companion object {

        fun newInstance() = ClassShopFragment()
    }
}