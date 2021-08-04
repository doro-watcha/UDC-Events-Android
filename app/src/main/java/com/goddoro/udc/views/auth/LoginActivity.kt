package com.goddoro.udc.views.auth

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.goddoro.common.common.NAVER_CLIENT_ID
import com.goddoro.common.common.NAVER_CLIENT_SECRET
import com.goddoro.common.common.observeOnce
import com.goddoro.common.util.Navigator
import com.goddoro.udc.databinding.ActivityLoginBinding
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding

    private val viewModel : LoginViewModel by viewModel()

    private lateinit var mOAuthLoginModule : OAuthLogin

    private val navigator : Navigator by inject()

    private val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                viewModel.fetchNaverData(mOAuthLoginModule.getAccessToken(this@LoginActivity))
            } else {
                val errorCode = mOAuthLoginModule.getLastErrorCode(this@LoginActivity).code
                val errorDesc = mOAuthLoginModule.getLastErrorDesc(this@LoginActivity)
                Toast.makeText(
                    this@LoginActivity, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(LayoutInflater.from(this))
        binding.vm = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)

        initOAuthSetting()
        observeViewModel()
    }

    private fun observeViewModel() {

        viewModel.apply {

            clickNaverLogin.observeOnce(this@LoginActivity){
                mOAuthLoginModule.startOauthLoginActivity(this@LoginActivity,mOAuthLoginHandler)
            }

            snsLoginCompleted.observeOnce(this@LoginActivity){
                navigator.startMainActivity(this@LoginActivity,true)
                finishAffinity()
            }
        }
    }

    private fun initOAuthSetting() {
        mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            this, NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, "UDC"
        )


    }
}