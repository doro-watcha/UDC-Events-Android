package com.goddoro.udc.views.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.api.AuthSignUpResponse
import com.goddoro.common.data.api.NaverUser
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.NaverRepository
import com.goddoro.common.util.TokenUtil
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val naverRepository: NaverRepository,
    private val tokenUtil: TokenUtil
) : ViewModel() {

    private val TAG = LoginViewModel::class.java.simpleName

    val snsLoginCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickNaverLogin : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickKakaoLogin : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickGoogleLogin : MutableLiveData<Once<Unit>> = MutableLiveData()

    fun onClickNaverLogin () {
        clickNaverLogin.value = Once(Unit)
    }

    fun onClickKakaoLogin () {
        clickKakaoLogin.value = Once(Unit)
    }

    fun onClickGoogleLogin () {
        clickGoogleLogin.value = Once(Unit)
    }

    fun fetchNaverData(accessToken: String) {

        debugE(TAG, accessToken)

        viewModelScope.launch {

            val user : NaverUser = naverRepository.getNaverUserData(accessToken)

            kotlin.runCatching {

                debugE(TAG, user)

                debugE(TAG, user.email)
                debugE(TAG, user.name)
                debugE(TAG, user.profileImgUrl)

                authRepository.snsSignUp(
                    loginId = user.id,
                    loginType = "naver",
                    username = user.name ?: "",
                    profileImgUrl = user.profileImgUrl ?: ""
                )

            }.onSuccess {
                loginCompleted(it)
            }.onFailure {

            }
        }
    }

    fun socialLogin (loginType : String , username : String , profileImgUrl : String, loginId : String ) {

        viewModelScope.launch {


            kotlin.runCatching {

                authRepository.snsSignUp(
                    loginId = loginId,
                    loginType = loginType,
                    username = username,
                    profileImgUrl = profileImgUrl
                )
            }.onSuccess {
                loginCompleted(it)
            }.onFailure {
                snsLoginCompleted.value = Once(Unit)
            }
        }
    }


    private fun loginCompleted ( response : AuthSignUpResponse) {
        authRepository.setCurrentUser(response.user)
        tokenUtil.clearToken()
        tokenUtil.saveToken(response.token)
        snsLoginCompleted.value = Once(Unit)

    }
}