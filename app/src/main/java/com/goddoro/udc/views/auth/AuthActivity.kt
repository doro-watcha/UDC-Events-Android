package com.goddoro.udc.views.auth

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.HasDefaultViewModelProviderFactory
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.*
import com.goddoro.common.data.api.response.UnWrappingDataException
import com.goddoro.common.util.Navigator
import com.goddoro.common.util.ToastUtil
import com.goddoro.udc.R
import com.goddoro.udc.databinding.ActivityAuthBinding
import dagger.android.AndroidInjection.inject
import dagger.android.support.DaggerAppCompatActivity
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import javax.inject.Inject

class AuthActivity : AppCompatActivity() {

    private val TAG = AuthActivity::class.java.simpleName

    private lateinit var mBinding : ActivityAuthBinding
    private val mViewModel : AuthViewModel by viewModel()

    private val navigator : Navigator by inject()

    private val mFragment1 = LoginFragment.newInstance()
    private val mFragment2 = SignUpFragment.newInstance()
    private val mFragment3 = EmailFindFragment.newInstance()
    private val mFragment4 = PasswordFindFragment.newInstance()
    private var mActiveFragment: Fragment = mFragment1

    private val toastUtil : ToastUtil by inject()

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

        fm.beginTransaction().add(R.id.mContainer, mFragment1, "1").show(mFragment1).commit()
        fm.beginTransaction().add(R.id.mContainer, mFragment2, "2").hide(mFragment2).commit()
        fm.beginTransaction().add(R.id.mContainer, mFragment3, "3").hide(mFragment3).commit()
        fm.beginTransaction().add(R.id.mContainer, mFragment4,"4").hide(mFragment4).commit()
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
            .replace(R.id.mContainer, willShow,null)
            .addToBackStack(null)
            .hide(mFragment1)
            .hide(mFragment2)
            .hide(mFragment3)
            .hide(mFragment4)
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


            loginCompleted.observeOnce(this@AuthActivity){
                debugE(TAG, "Login Completed")
                navigator.startMainActivity(this@AuthActivity,true)
                finish()

            }

            signUpCompleted.observeOnce(this@AuthActivity){
                toastUtil.createToast("회원가입이 완료되었습니다").show()
                changeFragment(1)
            }

            errorInvoked.observeOnce(this@AuthActivity){
                debugE(TAG, it)
                if ( it is UnWrappingDataException) {
                    debugE(TAG, "zxcv")
                    val errorMessage = when (it.errorCode) {
                        102 -> "이메일과 비밀번호를 모두 입력해주세요"
                        400 -> "존재하지 않는 유저입니다"
                        500 -> "이미 존재하는 이메일입니다"
                        else -> it.message ?: ""
                    }

                    toastUtil.createToast(errorMessage).show()
                }
            }

        }
    }


}