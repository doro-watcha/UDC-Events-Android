package com.goddoro.common.repositoryTest

import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.goddoro.common.common.debugE
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.di.apiModule
import com.goddoro.common.di.repositoryModule
import com.goddoro.common.di.utilModule
import com.goddoro.common.mock.FakeAuthInfo
import com.goddoro.common.mock.fakeNetworkModule
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
@LargeTest
class AuthRepositoryTest : AutoCloseKoinTest() {

    private val TAG = AuthRepositoryTest::class.java.simpleName

    private lateinit var repository: AuthRepository


    private val mainThreadSurrogate = newSingleThreadContext("UI thread")


    @Before
    fun setUp() {
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(apiModule)
            modules(fakeNetworkModule)
            modules(repositoryModule)
            modules(utilModule)
        }

        repository = get()

//        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `sign in with email`() = runBlocking {

        launch(Dispatchers.IO){
            kotlin.runCatching {
                repository.signIn(FakeAuthInfo.email, FakeAuthInfo.password)
            }.onSuccess {
                debugE(TAG,it.token)
            }.onFailure {
                debugE(TAG, it.message)
            }

        }





    }

    @After
    fun tearDown() {
      //  Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }


}