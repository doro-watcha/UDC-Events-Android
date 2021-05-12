package com.goddoro.udc.views.admin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.udc.databinding.ActivityAdminBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityAdminBinding
    private val mViewModel : AdminViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAdminBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        observeViewModel()
    }

    private fun initView() {


    }


    private fun observeViewModel(){


    }

    companion object {
        fun newIntent ( context : Context) : Intent {
            return Intent (context, AdminActivity::class.java)
        }
    }
}