package com.goddoro.udc.views.auth

import androidx.lifecycle.*
import com.goddoro.common.common.Once
import com.goddoro.common.common.StrPatternChecker
import com.goddoro.common.common.debugE
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.util.AppPreference
import com.goddoro.common.util.TokenUtil
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/08/15
 */

class AuthViewModel (
    private val authRepository : AuthRepository,
    private val tokenUtil: TokenUtil
) : ViewModel() {

    private val TAG = AuthViewModel::class.java.simpleName

    /**
     *  Login
     */

    val email : MutableLiveData<String> = MutableLiveData()
    val password : MutableLiveData<String> = MutableLiveData()

    val emailPatternOk = MediatorLiveData<Boolean>().apply {
        addSource(email){
            this.value = StrPatternChecker.EmailTypeOk(it)
        }
    }

    val passwordPatternOk = MediatorLiveData<Boolean>().apply {

        addSource(password){
            this.value = StrPatternChecker.PwdTypeOk(it)
        }
    }

    val isClickableLoginButton = MediatorLiveData<Boolean>().apply {

        addSource(emailPatternOk) {
            this.value = emailPatternOk.value == true && passwordPatternOk.value == true
        }

        addSource(passwordPatternOk){
            this.value = emailPatternOk.value == true && passwordPatternOk.value == true
        }
    }
    val clickNaverLogin : MutableLiveData<Once<Unit>> = MutableLiveData()


    /**
     * Sign Up
     */
    val signUpEmail : MutableLiveData<String> = MutableLiveData()
    val signUpPassword : MutableLiveData<String> = MutableLiveData()


    val signUpEmailPatternOk = MediatorLiveData<Boolean>().apply {
        addSource(signUpEmail){
            this.value = StrPatternChecker.EmailTypeOk(it)
        }
    }

    val signUpPasswordPatternOk = MediatorLiveData<Boolean>().apply {

        addSource(signUpPassword){
            this.value = StrPatternChecker.PwdTypeOk(it)
        }

    }


    val clickFindEmailPage : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickFindPasswordPage : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickSignUpPage : MutableLiveData<Once<Unit>> = MutableLiveData()
    val clickSignUp : MutableLiveData<Once<Unit>> = MutableLiveData()

    val signUpCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val loginCompleted : MutableLiveData<Once<User>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()
    // endregion


    init {

        //debugE(TAG, authRepository.curUser.value)

    }


    fun onClickSignUp () {

        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signUp(signUpEmail.value!!, signUpPassword.value!!)
            }.onSuccess {
                onClickLogin()
                signUpCompleted.value = Once(Unit)
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }

    fun onClickLogin () {

        viewModelScope.launch {
            kotlin.runCatching {
                authRepository.signIn(email.value!!, password.value!!)
            }.onSuccess {
                tokenUtil.saveToken(it.token)
                authRepository.setCurrentUser(it.user)
                loginCompleted.value = Once(it.user)
            }.onFailure {
                errorInvoked.value = Once(it)
            }
        }
    }


    fun onClickNaverLogin () {
        clickNaverLogin.value = Once(Unit)
    }


    fun onClickFindEmailPage() {
        clickFindEmailPage.value = Once(Unit)
    }

    fun onClickFindPasswordPage() {
        clickFindPasswordPage.value = Once(Unit)
    }

    fun onClickSignUpPage () {
        clickSignUpPage.value = Once(Unit)
    }
}