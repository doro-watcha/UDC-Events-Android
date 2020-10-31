package com.goddoro.common.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.common.databinding.ActivityNeedLoginBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NeedLoginActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityNeedLoginBinding
    private val mViewModel : NeedLoginViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityNeedLoginBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        observeViewModel()
    }

    private fun observeViewModel() {




    }
}