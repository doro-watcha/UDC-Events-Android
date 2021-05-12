package com.goddoro.udc.views.classShop.create

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import com.goddoro.udc.databinding.ActivityMakeClassBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MakeClassActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityMakeClassBinding
    private val mViewModel : MakeClassViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMakeClassBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        observeViewModel()
        initView()
    }

    private fun initView() {



    }

    private fun observeViewModel() {


    }

    companion object {

        fun newIntent ( context : Context) : Intent {
            return Intent ( context,  MakeClassActivity::class.java)
        }
    }

}