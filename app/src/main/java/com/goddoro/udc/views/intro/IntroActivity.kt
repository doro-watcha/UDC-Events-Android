package com.goddoro.udc.views.intro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.goddoro.common.extension.disposedBy
import com.goddoro.common.extension.rxSingleTimer
import com.goddoro.udc.MainActivity
import com.goddoro.udc.databinding.ActivityIntroBinding
import com.goddoro.udc.util.startActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class IntroActivity : AppCompatActivity() {

    private val TAG = IntroActivity::class.java.simpleName

    private val compositeDisposable = CompositeDisposable()

    private lateinit var mBinding : ActivityIntroBinding
    private val mViewModel : IntroViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityIntroBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        setContentView(mBinding.root)

        initView()
    }

    private fun initView() {

        rxSingleTimer(2000){
            startActivity(MainActivity::class)
            finish()
        }.disposedBy(compositeDisposable)
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
    }
}