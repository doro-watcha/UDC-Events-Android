package com.goddoro.udc.views.event

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.observe
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.common.widget.GridSpacingItemDecoration
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.extension.rxSingleTimer
import com.goddoro.common.util.Navigator
import com.goddoro.udc.R
import com.goddoro.udc.databinding.FragmentEventBinding
import com.goddoro.udc.util.setCurrentItem
import com.goddoro.udc.util.startActivity
import com.goddoro.udc.views.admin.AdminActivity
import com.goddoro.udc.views.event.adapter.BlurredAdapter
import com.goddoro.udc.views.event.adapter.GridPosterAdapter
import com.goddoro.udc.views.event.adapter.MainPosterAdapter
import com.goddoro.udc.views.event.adapter.PosterAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * created By DORO 2020/08/16
 */

class EventFragment : Fragment() {

    private val TAG = EventFragment::class.java.simpleName

    /**
     * Binding Instance
     */
    private lateinit var mBinding: FragmentEventBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: EventViewModel by viewModel()

    private var adminClickCount = 5

    private val navigator: Navigator by inject()

    private val compositeDisposable = CompositeDisposable()
    private val autoScrollDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentEventBinding.inflate(inflater, container, false).also { mBinding = it }.root

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

            debugE(TAG, "setupViewPager In EventFragment")

            adapter = MainPosterAdapter().apply {

                clickEvent.subscribe({
                    navigator.startEventDetailActivity(requireActivity(),it.first, it.second)
                },{
                    debugE(TAG,it)
                }).disposedBy(compositeDisposable)
            }

            var centerValue = Integer.MAX_VALUE / 100

            val findFirstPosition = centerValue % (mViewModel.mainEvents.value?.size ?: 1)

            centerValue -= findFirstPosition

            debugE(TAG, "$centerValue in setupViewPager")
            setCurrentItem(centerValue, false)

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


            this.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)

                    when (state) {
                        ViewPager2.SCROLL_STATE_IDLE -> {

                            scrollToNext()

                            val position = mBinding.mViewPager2.currentItem
                            if (position % (mViewModel.mainEvents.value?.size
                                    ?: 1) != (mBinding.mViewPagerBlurred.currentItem)
                            ) {

                                mBinding.mViewPagerBlurred.setCurrentItem(
                                    position % (mViewModel.mainEvents.value?.size ?: 1),
                                    false
                                )
                                mBinding.pageIndicator.refresh(
                                    position % (mViewModel.mainEvents.value?.size ?: 1)
                                )
                            }

                        }

                        ViewPager2.SCROLL_STATE_DRAGGING -> {
                            autoScrollDisposable.clear()
                        }
                    }
                }
            })

            offscreenPageLimit = 10
        }


    }

    private fun scrollToNext() {

        debugE(TAG, mViewModel.mainEvents.value?.size )

        if ( mViewModel.mainEvents.value?.size ?: -1 <= 0) return

        autoScrollDisposable.clear()
        rxSingleTimer(1000) {

            val position = mBinding.mViewPager2.currentItem + 1

            debugE(TAG, "$position in scroll To Next ")

            mBinding.pageIndicator.refresh(position % (mViewModel.mainEvents.value?.size ?: 1))
            mBinding.mViewPagerBlurred.setCurrentItem(
                position % (mViewModel.mainEvents.value?.size ?: 1), false
            )
            mBinding.mViewPager2.setCurrentItem(position, 600)

        }.disposedBy(autoScrollDisposable)


    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)

        debugE(TAG, hidden)

        if (hidden) {
            autoScrollDisposable.clear()
        } else {
            scrollToNext()
        }
    }

    override fun onStop() {
        super.onStop()

        autoScrollDisposable.clear()
    }

    override fun onResume() {
        super.onResume()

        scrollToNext()
    }


    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
        autoScrollDisposable.dispose()

    }

    private fun setupList() {

        mBinding.apply {


            listNewEvent.apply {

                adapter = PosterAdapter().apply {

                    clickEvent.subscribe({
                        debugE(TAG, it)
                        navigator.startEventDetailActivity(requireActivity(), it.first, it.second)
                    }, {
                        debugE(TAG, it)
                    }).disposedBy(compositeDisposable)


                }
            }

            listHotEvent.apply {
                adapter = PosterAdapter().apply {

                    clickEvent.subscribe({
                        navigator.startEventDetailActivity(requireActivity(), it.first, it.second)
                    }, {
                        debugE(TAG, it)
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

                    clickEvent.subscribe({
                        navigator.startEventDetailActivity(requireActivity(), it.first, it.second)
                    }, {
                        debugE(TAG, it)
                    }).disposedBy(compositeDisposable)
                }
            }
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {


            mainEvents.observe(viewLifecycleOwner, {

                if (it.isNotEmpty()) {
                    debugE(TAG, it.map { it.id })

                    setupViewPager()
                    mBinding.pageIndicator.refresh(mBinding.mViewPagerBlurred.currentItem)
                }
            })

            clickSearch.observeOnce(viewLifecycleOwner) {
                navigator.startSearchActivity(requireActivity())
            }

            clickUpload.observeOnce(viewLifecycleOwner) {
                navigator.startUploadEventActivity(requireActivity())
            }

            errorInvoked.observeOnce(viewLifecycleOwner) {
                debugE(TAG, it.message)
            }
        }
    }


    companion object {
        fun newInstance() = EventFragment()
    }

}