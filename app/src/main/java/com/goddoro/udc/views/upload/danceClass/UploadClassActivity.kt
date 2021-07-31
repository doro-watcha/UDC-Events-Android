package com.goddoro.udc.views.upload.danceClass

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.goddoro.udc.databinding.ActivityUploadClassBinding
import com.goddoro.udc.views.upload.EventPreviewFragment
import com.goddoro.udc.views.upload.UploadEventFragment

class UploadClassActivity : AppCompatActivity() {

    private val TAG = UploadClassActivity::class.java.simpleName

    private lateinit var binding : ActivityUploadClassBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUploadClassBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        observeViewModel()
        initViewPager()
    }

    private fun initViewPager() {

        binding.viewPager.apply {

            adapter = UploadClassPager(supportFragmentManager,3)
        }
    }

    private fun observeViewModel() {



    }

    inner class UploadClassPager (fragmentManager: FragmentManager, pageCount: Int) :
        FragmentStateAdapter(fragmentManager, lifecycle) {
        private val _count: Int = pageCount


        override fun getItemCount(): Int {
            return _count
        }

        override fun createFragment(position: Int): Fragment {
            return when ( position ) {

                0 -> UploadClassBasicFragment.newInstance()
                1 -> UploadClassImageFragment.newInstance()
                else -> UploadClassSpecificFragment.newInstance()
            }
        }

    }

}