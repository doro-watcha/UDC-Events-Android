package com.goddoro.udc.views.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.observeOnce
import com.goddoro.udc.MainViewModel
import com.goddoro.udc.databinding.ActivityLoginBinding
import com.goddoro.udc.di.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class LoginActivity : DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    private lateinit var mBinding : ActivityLoginBinding
    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel by lazy { ViewModelProvider(this)[LoginViewModel::class.java] }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this))

        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        observeViewModel()
    }

    private fun observeViewModel() {

        mViewModel.apply {

            loginCompleted.observeOnce(this@LoginActivity){
                Toast.makeText(this@LoginActivity,"로그인에 성공하였습니다", Toast.LENGTH_SHORT).show()
            }
            loginFailed.observeOnce(this@LoginActivity){
                Toast.makeText(this@LoginActivity, "로그인에 실패하였습니다", Toast.LENGTH_SHORT).show()
            }

        }
    }
}