package com.goddoro.udc.views.onboarding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.udc.databinding.ActivityOnBoardingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class OnBoardingActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityOnBoardingBinding
    private val mViewModel : OnBoardingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityOnBoardingBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        observeViewModel()
        setupViewPager()
    }

    private fun setupViewPager () {


    }

    private fun observeViewModel() {


    }
}