package com.goddoro.udc.views.tag

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivityTagDetailBinding
import com.google.android.material.tabs.TabLayout
import org.koin.androidx.viewmodel.ext.android.viewModel

class TagDetailActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityTagDetailBinding
    private val mViewModel : TagDetailViewModel by viewModel()

    private var position = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityTagDetailBinding.inflate(LayoutInflater.from(this))

        position = intent?.getIntExtra(ARG_POSITION,0) ?: 0

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        observeViewModel()
        setupViewPagerWithTab()
    }

    private fun observeViewModel() {

        mViewModel.apply {



        }

    }

    private fun setupViewPagerWithTab() {

        mBinding.apply {
            mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.common_genre)), 0)
            mTabLayout.addTab(mTabLayout.newTab().setText(getString(R.string.common_dancer)), 1)

            /**
             * 2. viewpagerAdapter 생성 / viewPager.adapter 로 설정
             */
            val viewPagerAdapter = TagDetailViewPagerAdapter(supportFragmentManager)
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

            mViewPager.offscreenPageLimit = 3
        }
    }

    inner class TagDetailViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

        override fun getCount(): Int {
            return 3
        }

        override fun getItem(position: Int): Fragment {
            return when (position) {
                0 -> TagGenreFragment.newInstance()
                1 -> TagArtistFragment.newInstance()
                else -> throw Exception("error")
            }
        }
    }

    companion object {

        private const val ARG_POSITION = "ARG_POSITION"

        fun newIntent ( activity : Context, position : Int) : Intent {
            val intent = Intent( activity, TagDetailActivity::class.java)
            intent.putExtra(ARG_POSITION, position)
            return intent
        }
    }
}