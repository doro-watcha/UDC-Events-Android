package com.goddoro.udc.views.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.goddoro.common.common.observeOnce
import com.goddoro.udc.databinding.ActivityUploadEventBinding
import com.goddoro.udc.di.ViewModelFactory
import com.goddoro.upload.R
import dagger.android.support.DaggerAppCompatActivity
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import javax.inject.Inject

class UploadEventActivity : DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    private val TAG = UploadEventActivity::class.java.simpleName

    private lateinit var mBinding : ActivityUploadEventBinding

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel by lazy { ViewModelProvider(this)[UploadEventViewModel::class.java] }


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        mBinding = ActivityUploadEventBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)

        observeViewModel()
    }

    private fun observeViewModel() {
        mViewModel.apply {

            curPoster.observe(this@UploadEventActivity){

            }

            clickPickImage.observeOnce(this@UploadEventActivity){
                TedImagePicker.with(this@UploadEventActivity)
                    .title(resources.getString(R.string.txt_pick_image))
                    .showCameraTile(false)
                    .mediaType(
                        MediaType.IMAGE
                    )
                    .start {
                        curPoster.value = it
                    }
            }

        }


    }
}