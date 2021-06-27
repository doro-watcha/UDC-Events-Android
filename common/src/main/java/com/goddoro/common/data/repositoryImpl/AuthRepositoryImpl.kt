package com.goddoro.common.data.repositoryImpl

import android.net.Uri
import android.os.Looper
import androidx.annotation.MainThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.goddoro.common.common.debugE
import com.goddoro.common.common.filterValueNotNull
import com.goddoro.common.data.api.AuthAPI
import com.goddoro.common.data.api.AuthSignInResponse
import com.goddoro.common.data.api.AuthSignUpResponse
import com.goddoro.common.data.model.User
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.util.AppPreference
import com.goddoro.common.util.MultiPartUtil
import com.goddoro.common.util.TokenUtil
import com.google.gson.Gson
import io.reactivex.Completable
import okhttp3.MultipartBody


/**
 * created By DORO 2020/10/03
 */

class AuthRepositoryImpl (
    private val api : AuthAPI,
    private val gson : Gson,
    private val appPreference: AppPreference,
    private val multiPartUtil: MultiPartUtil,
    private val tokenUtil: TokenUtil
) : AuthRepository {

    private val TAG = AuthRepositoryImpl::class.java.simpleName

    private val _curUser: MutableLiveData<User?> = MutableLiveData()
    override val curUser: LiveData<User?> = _curUser

    init {
        try {
            appPreference.curUser?.run {
                gson.fromJson(this, User::class.java)?.run {
                    _curUser.value = this
                }
            }
        } catch (t: Throwable) {
            debugE(TAG, "SP 에서 유저 불러오기 실패 $t")
        }
    }

    override fun setCurrentUser(user: User) {
        appPreference.curUser = gson.toJson(user)
        if (Looper.getMainLooper() == Looper.myLooper())
            this._curUser.value = user
        else
            this._curUser.postValue(user)
    }

    override fun isSignedIn() : Boolean {
        return curUser.value != null
    }

    override suspend fun signIn(loginId: String, password: String): AuthSignInResponse {
        return api.signIn(loginId,password).unWrapData()
    }

    override suspend fun signUp(loginId: String, password: String,username : String ): Completable {

        return api.signUp(loginId,password,username).unWrapCompletable()
    }

    override suspend fun snsSignUp(
        loginId: String,
        username: String,
        loginType: String,
        profileImgUrl : String
    ): AuthSignUpResponse {

        val params = hashMapOf(
            "loginId" to loginId,
            "username" to username,
            "loginType" to loginType,
            "profileImgUrl" to profileImgUrl
        ).filterValueNotNull()

        return api.snsSignUp(params).unWrapData()
    }

    @MainThread
    override fun signOut(): Boolean {
        val result = curUser.value != null

        tokenUtil.clearToken()

        if (Looper.getMainLooper() == Looper.myLooper())
            _curUser.value = null
        else
            _curUser.postValue(null)

        //loginUtil.signOut()
        appPreference.curUser = null
        return result
    }





}