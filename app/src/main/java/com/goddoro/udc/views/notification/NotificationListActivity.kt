package com.goddoro.udc.views.notification

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.view.LayoutInflater
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.udc.databinding.ActivityNotificationListBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotificationListActivity : AppCompatActivity() {

    private val TAG = NotificationListActivity::class.java.simpleName

    private lateinit var mBinding : ActivityNotificationListBinding

    private val mViewModel : NotificationViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityNotificationListBinding.inflate(LayoutInflater.from(this))

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = this

        setContentView(mBinding.root)
        initView()
        setupRecyclerView()
        observeViewModel()
    }

    private fun initView() {

        mBinding.toolbar.apply {

            setLeftIconClickListener {
                finish()
            }
        }
    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {
            adapter = NotificationAdapter()

        }

    }

    private fun observeViewModel() {


        mViewModel.apply {

            errorInvoked.observeOnce(this@NotificationListActivity){
                debugE(TAG, it.message)
            }
        }
    }

    companion object {

        fun newIntent ( activity : Activity) : Intent {
            return Intent ( activity, NotificationListActivity::class.java)
        }
    }
}