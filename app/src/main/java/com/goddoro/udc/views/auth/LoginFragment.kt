package com.goddoro.udc.views.auth

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.goddoro.common.common.NAVER_CLIENT_ID
import com.goddoro.common.common.NAVER_CLIENT_SECRET
import com.goddoro.common.common.debugE
import com.goddoro.common.common.observeOnce
import com.goddoro.udc.databinding.FragmentLoginBinding
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton
import dagger.android.support.DaggerFragment


/**
 * created By DORO 2020/09/12
 */

class LoginFragment : DaggerFragment() {

    private val TAG = LoginFragment::class.java.simpleName

    /**
     * Binding Instance
     */
    private lateinit var mBinding: FragmentLoginBinding

    /**
     * ViewModel Instance
     */
    private val mViewModel: AuthViewModel by lazy {
        ViewModelProvider(requireActivity())[AuthViewModel::class.java]
    }

    private lateinit var mOAuthLoginButton: OAuthLoginButton
    private lateinit var mOAuthLoginModule : OAuthLogin

    private val mOAuthLoginHandler: OAuthLoginHandler = @SuppressLint("HandlerLeak")
    object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
                val accessToken = mOAuthLoginModule.getAccessToken(context)
                val refreshToken = mOAuthLoginModule.getRefreshToken(context)
                val expiresAt = mOAuthLoginModule.getExpiresAt(context)
                val tokenType = mOAuthLoginModule.getTokenType(context)
//                mOauthAT.setText(accessToken)
//                mOauthRT.setText(refreshToken)
//                mOauthExpires.setText(expiresAt.toString())
//                mOauthTokenType.setText(tokenType)
//                mOAuthState.setText(mOAuthLoginModule.getState(mContext).toString())
            } else {
                val errorCode = mOAuthLoginModule.getLastErrorCode(context).code
                val errorDesc = mOAuthLoginModule.getLastErrorDesc(context)
                Toast.makeText(
                    context, "errorCode:" + errorCode
                            + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentLoginBinding.inflate(inflater, container, false).also { mBinding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        debugE(TAG, mViewModel)

        mBinding.vm = mViewModel
        mBinding.lifecycleOwner = viewLifecycleOwner

        initOAuthSetting()
        observeViewModel()
    }


    private fun initOAuthSetting() {
        mOAuthLoginModule = OAuthLogin.getInstance()
        mOAuthLoginModule.init(
            requireActivity(), NAVER_CLIENT_ID, NAVER_CLIENT_SECRET, "UDC"
        )


    }

    private fun observeViewModel() {
        mViewModel.apply {

            clickNaverLogin.observeOnce(viewLifecycleOwner){

                debugE(TAG, "NAVER LOGIN")
                mOAuthLoginModule.startOauthLoginActivity(requireActivity(),mOAuthLoginHandler)
            }
        }

    }

    companion object {

        fun newInstance() = LoginFragment()
    }
}