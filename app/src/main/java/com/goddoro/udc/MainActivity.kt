package com.goddoro.udc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.observeOnce
import com.goddoro.udc.databinding.ActivityMainBinding
import com.goddoro.udc.di.AppComponent
import com.goddoro.udc.di.NetworkModule
import com.goddoro.udc.di.ViewModelFactory
import com.goddoro.udc.views.login.LoginActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity :  DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    private lateinit var mBinding : ActivityMainBinding

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel by lazy { ViewModelProvider(this)[MainViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel


        setContentView(mBinding.root)

        initView()

        observeViewModel()
    }

    private fun initView() {


    }

    private fun observeViewModel() {

        mViewModel.apply {

            clickToLogin.observeOnce(this@MainActivity){
                val intent = Intent ( this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }
}