package com.goddoro.udc.views.event.detail

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.observeOnce
import com.goddoro.udc.databinding.ActivityEventDetailBinding
import com.goddoro.udc.views.upload.UploadEventViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import javax.inject.Inject

class EventDetailActivity : AppCompatActivity() {

    private lateinit var mBinding : ActivityEventDetailBinding

    private lateinit var  mViewModel : EventDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityEventDetailBinding.inflate(LayoutInflater.from(this))

        mViewModel = getViewModel{ parametersOf( intent?.getIntExtra(ARG_EVENT_ID,0)) }

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        initView()
        observeViewModel()
    }

    private fun initView() {

        mBinding.txtEventTitle.isSelected = true
    }

    private fun observeViewModel() {


        mViewModel.apply {

            clickBackArrow.observeOnce(this@EventDetailActivity){
                finish()
            }

        }
    }


    companion object {
        private const val ARG_EVENT_ID = "ARG_EVENT_ID"

        fun newIntent (activity : Activity, eventId : Int ) : Intent {

            val intent = Intent ( activity, EventDetailActivity::class.java )
            intent.putExtra(ARG_EVENT_ID,eventId)

            return intent
        }

    }
}