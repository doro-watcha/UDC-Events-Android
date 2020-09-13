package com.goddoro.udc.views.auth

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.*
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivityAuthBinding
import com.goddoro.udc.di.ViewModelFactory
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class AuthActivity : DaggerAppCompatActivity(), HasDefaultViewModelProviderFactory {

    private val TAG = AuthActivity::class.java.simpleName

    private lateinit var mBinding : ActivityAuthBinding

    @Inject
    lateinit var viewModelFactory : ViewModelFactory

    override fun getDefaultViewModelProviderFactory() = viewModelFactory

    private val mViewModel by lazy { ViewModelProvider(this)[AuthViewModel::class.java] }


    private val mFragment1 = LoginFragment.newInstance()
    private val mFragment2 = SignUpFragment.newInstance()
    private val mFragment3 = EmailFindFragment.newInstance()
    private val mFragment4 = PasswordFindFragment.newInstance()
    private var mActiveFragment: Fragment = mFragment1



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityAuthBinding.inflate(LayoutInflater.from(this))

        debugE(TAG, mViewModel)
        mBinding.lifecycleOwner = this
        mBinding.vm = mViewModel

        setContentView(mBinding.root)

        if ( savedInstanceState == null)  {
            initFragments()
        }

        observeViewModel()
    }



    private fun initFragments() {

        val fm = supportFragmentManager

        fm.beginTransaction().add(R.id.mContainer, mFragment1, "1").commit()
    }


    private fun changeFragment(position: Int) {

        val willShow = when (position) {
            1 -> mFragment1
            2 -> mFragment2
            3 -> mFragment3
            4 -> mFragment4
            else -> throw IllegalArgumentException()
        }
        supportFragmentManager.beginTransaction()



        supportFragmentManager.beginTransaction()
            .add(R.id.mContainer, willShow,null)
            .addToBackStack(null)
            .show(willShow)
            .commit()
        mActiveFragment = willShow
    }

    private fun observeViewModel () {


        mViewModel.apply {

            clickSignUpPage.observeOnce(this@AuthActivity){
                changeFragment(2)
            }

            clickFindEmailPage.observeOnce(this@AuthActivity){
                changeFragment(3)
            }

            clickFindPasswordPage.observeOnce(this@AuthActivity){
                changeFragment(4)
            }

            errorInvoked.observeOnce(this@AuthActivity){
                debugE(TAG, it)
            }

        }
    }


}