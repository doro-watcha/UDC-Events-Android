package com.goddoro.udc.views.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.common.Once
import com.goddoro.common.common.debugE
import com.goddoro.common.data.api.NaverUser
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.NaverRepository
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val naverRepository: NaverRepository
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

                authRepository.snsSignUp(
                    loginId = user.email ?: "",
                    loginType = "naver",
                    username = user.name ?: "",
                    profileImgUrl = user.profileImgUrl ?: ""
                )

            }.onSuccess {
                authRepository.setCurrentUser(it.user)
                snsLoginCompleted.value = Once(Unit)
            }.onFailure {

            }
        }
    }

    fun socialLogin (loginType : String , username : String , profileImgUrl : String ) {

        viewModelScope.launch {


            kotlin.runCatching {

                authRepository.snsSignUp(
                    loginId ="test sample",
                    loginType = loginType,
                    username = username,
                    profileImgUrl = profileImgUrl
                )
            }.onSuccess {
                authRepository.setCurrentUser(it.user)
            }.onFailure {
                snsLoginCompleted.value = Once(Unit)
            }
        }
    }
}