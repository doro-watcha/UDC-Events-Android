package com.goddoro.udc.views.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.goddoro.common.common.debugE
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentHomeBinding
import com.goddoro.udc.views.auth.AuthActivity
import com.goddoro.udc.views.upload.UploadEventActivity
import dagger.android.support.DaggerFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject
import androidx.fragment.app.Fragment
import com.goddoro.common.Broadcast
import com.goddoro.common.common.observeOnce
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.extension.rxRepeatTimer
import com.goddoro.common.util.Navigator
import com.goddoro.udc.util.startActivity
import com.goddoro.udc.views.admin.AdminActivity
import com.goddoro.udc.views.event.detail.EventDetailActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Thread.sleep

/**
 * created By DORO 2020/08/16
 */

class HomeFragment : Fragment() {

    private val TAG = HomeFragment::class.java.simpleName

    /**
     * Binding Instance
     */
    private lateinit var mBinding: FragmentHomeBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: HomeViewModel by viewModel()

    private var adminClickCount = 5

    private val navigator : Navigator by inject()

    private val compositeDisposable = CompositeDisposable()
    private val autoScrollDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        mBinding.lifecycleOwner = viewLifecycleOwner
        mBinding.vm = mViewModel


        initView()
        observeViewModel()
        setupList()


        setupBlurredImage()

    }

    private fun initView() {

        mBinding.txtTitleNewEvent.apply {
            this.setOnClickListener {
                Handler().postDelayed({ adminClickCount = 5 }, 3000)

                adminClickCount--
                if (adminClickCount == 0) {
                    startActivity(AdminActivity::class)
                }
            }


        }


    }


    private fun setupBlurredImage() {

        mBinding.mViewPagerBlurred.apply {

            adapter = BlurredAdapter()
            isUserInputEnabled = false
            offscreenPageLimit = 10

        }
    }


    private fun setupViewPager() {

        mBinding.mViewPager2.apply {


            adapter = MainPosterAdapter().apply {

                clickEvent.subscribe{
                    navigator.startEventDetailActivity(requireActivity(),it.first,it.second)
                }.disposedBy(compositeDisposable)

            }

            var centerValue =  Integer.MAX_VALUE / 2

            val findFirstPosition = centerValue % ( mViewModel.mainEvents.value?.size ?: 0)

            centerValue -= findFirstPosition


            setCurrentItem( centerValue , false )

            val pageMarginPx = resources.getDimensionPixelOffset(R.dimen.pageMargin)
            val offsetPx = resources.getDimensionPixelOffset(R.dimen.offset)
            this.setPageTransformer { page, position ->
                val viewPager = page.parent.parent as ViewPager2
                val offset = position * -(2 * offsetPx + pageMarginPx)
                if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                    if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                        page.translationX = -offset
                    } else {
                        page.translationX = offset
                    }
                } else {
                    page.translationY = offset
                }
            }
            this.setPadding(offsetPx, offsetPx, offsetPx, offsetPx)


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

                    mViewModel.currentPage.value = position
                    mViewModel.curPoster.value = mViewModel.mainEvents.value!![position % ( mViewModel.mainEvents.value?.size ?: 0)]
                    mBinding.mViewPagerBlurred.setCurrentItem(mBinding.mViewPager2.currentItem % ( mViewModel.mainEvents.value?.size ?: 0), false)
                    mBinding.indicator.selection = position % ( mViewModel.mainEvents.value?.size ?: 0)
                }

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }
            })

            offscreenPageLimit = 10
        }


    }

    private fun setupList() {

        mBinding.apply {



            listNewEvent.apply {

                adapter = PosterAdapter().apply{

                    clickEvent.subscribe{
                        debugE(TAG, it )
                        navigator.startEventDetailActivity(requireActivity(),it.first ,it.second)
                    }.disposedBy(compositeDisposable)

                }
            }

            listHotEvent.apply {
                adapter = PosterAdapter().apply{

                    clickEvent.subscribe({
                        navigator.startEventDetailActivity(requireActivity(), it.first, it.second)
                    },{
                        debugE(TAG,it)
                    }).disposedBy(compositeDisposable)
                }
            }


            listStaffPickEvent.apply {

                val mVideoGridLayoutManager: LinearLayoutManager = GridLayoutManager(context, 2)

                // bookmark video : grid , challenge : list
                val spacingTop = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()
                val spacingLeft = resources.getDimension(R.dimen.paddingItemDecoration4).toInt()

                val mVideoGridSpacing =
                    GridSpacingItemDecoration(2, spacingLeft, spacingTop, spacingLeft)

                layoutManager = mVideoGridLayoutManager
                addItemDecoration(mVideoGridSpacing)

                //isNestedScrollingEnabled = false
                adapter = GridPosterAdapter().apply {

                    clickEvent.subscribe {
                        navigator.startEventDetailActivity(requireActivity(), it.first,it.second)
                    }.disposedBy(compositeDisposable)
                }
            }
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {


            mainEvents.observe(viewLifecycleOwner){

                if ( it.isNotEmpty()) {
                    debugE(TAG, it.map { it.id })

                    mBinding.indicator.count = it.size

                    setupViewPager()
                    //   startAutoScroll()
                }
            }

            clickSearch.observeOnce(viewLifecycleOwner){
                navigator.startSearchActivity(requireActivity())
            }

            clickUpload.observeOnce(viewLifecycleOwner){
                navigator.startUploadEventActivity(requireActivity())
            }

            errorInvoked.observeOnce(viewLifecycleOwner){
                debugE(TAG, it.message)
            }
        }
    }

    private fun startAutoScroll () {


        autoScrollDisposable.clear()
        rxRepeatTimer(5000,{
            mBinding.mViewPager2.apply {

                //sleep(5000)

                setCurrentItem(currentItem + 1 , true)
            }

        },5000).disposedBy(autoScrollDisposable)


    }


    override fun onResume() {
        super.onResume()

        startAutoScroll()
    }

    override fun onPause() {
        super.onPause()

        autoScrollDisposable.clear()
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

}