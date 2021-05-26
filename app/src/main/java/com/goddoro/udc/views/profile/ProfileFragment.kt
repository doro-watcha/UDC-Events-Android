package com.goddoro.udc.views.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.fragment.app.Fragment
import com.goddoro.common.common.AutoClearedValue
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.util.Navigator
import com.goddoro.udc.util.underConstruction.UnderConstructionFragment
import dagger.android.support.AndroidSupportInjection.inject
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

/**
 * created By DORO 2020/08/16
 */

class ProfileFragment : Fragment() {

    private val TAG = ProfileFragment::class.java.simpleName

    /**
     * Binding Instance
     */
    private lateinit var  mBinding: FragmentProfileBinding

    /**
     * ViewModel Instance
     */
    private lateinit var  mViewModel : ProfileViewModel

    private val navigator : Navigator by inject()

    private val compositeDisposable = CompositeDisposable()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentProfileBinding.inflate(inflater,container,false).also{mBinding=it}.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewModel = getViewModel{parametersOf(arguments?.getInt(ARG_AUTHOR_ID))}

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

        mViewModel.apply {

            clickSetting.observeOnce(viewLifecycleOwner){
                navigator.startSettingActivity(requireActivity())
            }

            clickNotification.observeOnce(viewLifecycleOwner){
                navigator.startNotificationListActivity(requireActivity())
            }

            errorInvoked.observeOnce(viewLifecycleOwner){
                debugE(TAG, it.message)
            }
        }

    }


    inner class ProfileTabAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return 2
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> EventCollectionFragment.newInstance()
                1 -> UnderConstructionFragment.newInstance("내가 참여한 행사에 대한 기능이 업데이트 될 예정입니다.")
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

        private const val ARG_AUTHOR_ID = "ARG_AUTHOR_ID"
        fun newInstance(userId: Int): ProfileFragment {
            val fragment = ProfileFragment()
            val args = Bundle()
            args.putInt(ARG_AUTHOR_ID, userId)
            fragment.arguments = args

            return fragment
        }
    }
}