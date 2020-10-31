package com.goddoro.udc.views.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.goddoro.common.Broadcast
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.dialog.showCommonDialog
import com.goddoro.udc.databinding.ActivityUploadEventBinding
import com.goddoro.upload.R
import dagger.android.support.DaggerAppCompatActivity
import gun0912.tedimagepicker.builder.TedImagePicker
import gun0912.tedimagepicker.builder.type.MediaType
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class UploadEventActivity :AppCompatActivity() {

    private val TAG = UploadEventActivity::class.java.simpleName

    private lateinit var mBinding : ActivityUploadEventBinding

    private val mViewModel : UploadEventViewModel by viewModel()


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

            clickTypeDialog.observeOnce(this@UploadEventActivity){
                val dialog = EventTypeDialog(object : EventTypeDialog.onClickTypeListener {
                    override fun onClickType(type: String) {
                        mViewModel.type.value = type
                    }
                })
                dialog.show(supportFragmentManager,dialog.tag)
            }
            clickUploadButton.observeOnce(this@UploadEventActivity){
                Broadcast.eventUploadBroadcast.onNext(Unit)
                finish()
            }

            clickBackArrow.observeOnce(this@UploadEventActivity){
                finish()
            }

            uploadCompleted.observeOnce(this@UploadEventActivity){
                finish()
            }
            errorInvoked.observeOnce(this@UploadEventActivity){
                debugE(TAG, it.message)
                showCommonDialog(R.string.dialog_error_unknown)
            }


        }


    }
}