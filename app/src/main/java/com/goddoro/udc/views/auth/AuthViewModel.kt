package com.goddoro.udc.views.auth

import androidx.lifecycle.*
import com.goddoro.common.common.Once
import com.goddoro.common.common.StrPatternChecker
import com.goddoro.udc.di.NetworkClient
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * created By DORO 2020/08/15
 */

class AuthViewModel @Inject constructor() : ViewModel() {

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
    val loginCompleted : MutableLiveData<Once<Unit>> = MutableLiveData()
    val errorInvoked : MutableLiveData<Once<Throwable>> = MutableLiveData()
    // endregion


    init {

    }


    fun onClickLogin () {


        viewModelScope.launch {
            kotlin.runCatching {
                NetworkClient.authService.signIn(email.value!!, password.value!!)
            }.onSuccess {
                loginCompleted.value = Once(Unit)
            }.onFailure {

                errorInvoked.value = Once(it)
            }
        }
    }

    fun onClickSignUp () {

        viewModelScope.launch {
            kotlin.runCatching {
                NetworkClient.authService.signUp(signUpEmail.value!!, signUpPassword.value!!, "2020-09-10")
            }.onSuccess {

            }.onFailure {

            }
        }
        clickSignUp.value = Once(Unit)
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