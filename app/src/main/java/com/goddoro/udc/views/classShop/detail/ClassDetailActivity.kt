package com.goddoro.udc.views.classShop.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.udc.databinding.ActivityClassDetailBinding
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ClassDetailActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityClassDetailBinding

    private lateinit var mViewModel : ClassDetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityClassDetailBinding.inflate(LayoutInflater.from(this))

        mViewModel = getViewModel{
            parametersOf(intent?.getIntExtra(ARG_CLASS_ID,0))
        }

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        setupViewPager()
        observeViewModel()
    }

    private fun setupViewPager() {

        mBinding.mViewPager2.apply {


            adapter
        }
    }
    private fun observeViewModel() {


        mViewModel.apply {


        }
    }

    companion object {

        private const val ARG_CLASS_ID = "ARG_CLASS_ID"

        fun newIntent (activity : Activity, classId : Int) : Intent  {

            val intent = Intent ( activity, ClassDetailActivity::class.java)
            intent.putExtra(ARG_CLASS_ID, classId)
            return intent
        }
    }
}