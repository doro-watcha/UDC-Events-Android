package com.goddoro.common.data.repository

import androidx.lifecycle.LiveData
import com.goddoro.common.data.api.AuthSignInResponse
import com.goddoro.common.data.model.User
import io.reactivex.Completable


/**
 * created By DORO 2020/10/03
 */

interface AuthRepository {

    /**
     * Current User Instance(me)
     */
    val curUser: LiveData<User?>

    fun setCurrentUser(user: User)

    fun isSignedIn() : Boolean

    suspend fun signIn (
        email : String,
        password : String
    ) : AuthSignInResponse

    suspend fun signUp (
        email : String,
        password : String,
        username : String
    ) : Completable

    fun signOut() : Boolean
}