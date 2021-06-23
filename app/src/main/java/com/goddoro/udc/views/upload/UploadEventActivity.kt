package com.goddoro.udc.views.upload

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.observe
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.goddoro.common.Broadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.dialog.showCommonDialog
import com.goddoro.common.util.Navigator
import com.goddoro.udc.databinding.ActivityUploadEventBinding
import com.goddoro.udc.views.upload.calendar.CalendarDialog
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class UploadEventActivity :AppCompatActivity() {

    private val TAG = UploadEventActivity::class.java.simpleName

    private lateinit var mBinding : ActivityUploadEventBinding

    private val mViewModel : UploadEventViewModel by viewModel()

    private val navigator : Navigator by inject()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        mBinding = ActivityUploadEventBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        setupViewPager()
        observeViewModel()

    }

    private fun setupViewPager() {

        mBinding.mViewPager.apply {

            adapter = UploadEventViewPager(supportFragmentManager,2)

            isUserInputEnabled = false
        }
    }

    private fun observeViewModel() {

        mViewModel.apply {

            clickPreview.observeOnce(this@UploadEventActivity){
                mBinding.mViewPager.currentItem = 1
            }

            clickBackStep.observeOnce(this@UploadEventActivity){
                mBinding.mViewPager.currentItem = 0
            }

            uploadCompleted.observeOnce(this@UploadEventActivity){
                Broadcast.eventUploadBroadcast.onNext(Unit)
                finish()
            }
        }
    }

    inner class UploadEventViewPager(fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when ( position ) {

                0 -> UploadEventFragment.newInstance()
                else -> EventPreviewFragment.newInstance()
            }
        }

    }

}