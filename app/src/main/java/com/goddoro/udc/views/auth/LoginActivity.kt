package com.goddoro.udc.views.auth

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.goddoro.common.common.NAVER_CLIENT_ID
import com.goddoro.common.common.NAVER_CLIENT_SECRET
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.common.util.Navigator
import com.goddoro.udc.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.kakao.auth.AuthType
import com.kakao.auth.ISessionCallback
import com.kakao.auth.Session
import com.kakao.network.ErrorResult
import com.kakao.usermgmt.UserManagement
import com.kakao.usermgmt.callback.MeV2ResponseCallback
import com.kakao.usermgmt.response.MeV2Response
import com.kakao.util.exception.KakaoException
import com.kakao.util.helper.log.Logger
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.ArrayList

class LoginActivity : AppCompatActivity() {

    private val TAG = LoginActivity::class.java.simpleName

    private lateinit var binding : ActivityLoginBinding

    private val viewModel : LoginViewModel by viewModel()

    private val navigator : Navigator by inject()

    /**
     * Naver Login
     */

    private lateinit var mOAuthLoginModule : OAuthLogin

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


    /**
     * Kakao Login
     */
    private var callback: SessionCallback? = null

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

            clickKakaoLogin.observeOnce(this@LoginActivity){
                Session.getCurrentSession().open(AuthType.KAKAO_TALK, this@LoginActivity)
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


    //region Kakao
    private inner class SessionCallback : ISessionCallback {
        override fun onSessionOpened() {
            // 성공
            val keys = ArrayList<String>()
            keys.add("properties.nickname")
            keys.add("properties.profile_image")

            UserManagement.getInstance().me(keys, object : MeV2ResponseCallback() {
                override fun onFailure(errorResult: ErrorResult?) {
                    val message = "failed to get user info. msg=" + errorResult!!
                    Logger.d(message)
                  //  authRepository.signOut()
                }

                override fun onSessionClosed(errorResult: ErrorResult) {
                    errorResult.errorMessage
                  //  authRepository.signOut()
                }

                override fun onSuccess(response: MeV2Response) {

                    val nickName = response.properties["nickname"] ?: ""
                    val socialImage = response.properties["profile_image"]

                    debugE(TAG, "kakao Login Try")

                    viewModel.socialLogin("kakao",nickName,socialImage ?: "")
                }
            })
        }

        override fun onSessionOpenFailed(exception: KakaoException) {
            //authRepository.signOut()
            // 카카오 로그인화면 진입했다가 back으로 돌아왔을경우.
            debugE(
                TAG,
                object {}::class.java.enclosingMethod?.name ?: "$TAG : Method Name Not Found"
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        debugE(TAG, "fuck!")

        // Kakao
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return
        }

        super.onActivityResult(requestCode, resultCode, data)

    }

}