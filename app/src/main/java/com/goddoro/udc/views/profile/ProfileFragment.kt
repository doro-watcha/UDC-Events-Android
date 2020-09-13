package com.goddoro.udc.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentHomeBinding
import com.goddoro.udc.databinding.FragmentProfileBinding
import com.goddoro.udc.views.home.HomeViewModel
import com.google.android.material.tabs.TabLayout
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject


/**
 * created By DORO 2020/08/16
 */

class ProfileFragment : DaggerFragment(), HasDefaultViewModelProviderFactory {

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentProfileBinding

    /**
     * ViewModel Instance
     */

    @Inject
    lateinit var viewModelFactory : ViewModelProvider.Factory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel: ProfileViewModel by lazy {
        ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
    }

    private val compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentProfileBinding.inflate(inflater,container,false).also{mBinding=it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel

        setupViewPagerWithTab()
        observeViewModel()


    }

    private fun setupViewPagerWithTab(){

        mBinding.apply {

            mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.txt_profile_tab_1)), 0)
            mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.txt_profile_tab_2)), 1)

            /**
             * 2. viewpagerAdapter 생성 / viewPager.adapter 로 설정
             */
            mViewPager.adapter = ProfileTabAdapter(childFragmentManager)


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

            mViewPager.offscreenPageLimit = 2

        }
    }

    private fun observeViewModel(){


    }


    inner class ProfileTabAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> EventCollectionFragment.newInstance()
                1 -> JoinEventFragment.newInstance()
                else -> throw Exception("error")
            }
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return when (position) {
                0 -> resources.getString(R.string.txt_profile_tab_1)
                else -> resources.getString(R.string.txt_profile_tab_2)
            }
        }

    }






    companion object {

        fun newInstance () = ProfileFragment()
    }
}