package com.goddoro.udc.views.admin

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.observe
import com.goddoro.common.common.observeOnce
import com.goddoro.common.extension.disposedBy
import com.goddoro.udc.databinding.ActivityAdminBinding
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel

class AdminActivity : AppCompatActivity() {

    private val TAG = AdminActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

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
        setupRecyclerView()
    }

    private fun initView() {


    }


    private fun observeViewModel(){

        mViewModel.apply {

            onGrantSuccess.observeOnce(this@AdminActivity){
                refresh()
                Toast.makeText(this@AdminActivity,"행사를 승인하였습니다", Toast.LENGTH_SHORT).show()
            }
            errorInvoked.observe(this@AdminActivity){
                Log.d(TAG, it.message.toString())
            }
        }

    }

    private fun setupRecyclerView() {

        mBinding.mRecyclerView.apply {

            adapter = UnConfirmedEventAdapter().apply {

                clickConfirm.subscribe{
                    mViewModel.onClickConfirm(it)
                }.disposedBy(compositeDisposable)
            }
        }
    }

    companion object {
        fun newIntent ( context : Context) : Intent {
            return Intent (context, AdminActivity::class.java)
        }
    }
}