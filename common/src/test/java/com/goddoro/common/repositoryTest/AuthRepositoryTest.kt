package com.goddoro.common.repositoryTest

import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.di.apiModule
import com.goddoro.common.di.repositoryModule
import com.goddoro.common.di.utilModule
import com.goddoro.common.mock.FakeAuthInfo
import com.goddoro.common.mock.fakeNetworkModule
import kotlinx.coroutines.*
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert
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

    private val testScope = TestCoroutineScope()


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

        Dispatchers.setMain(mainThreadSurrogate)
    }

    @Test
    fun `sign in with email`() = runBlockingTest {


        val result = repository.signIn(FakeAuthInfo.email, FakeAuthInfo.password)

        launch(Dispatchers.Main){
            assert(result.token.length > 1 )
        }
        this.cancel()


    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
        testScope.cleanupTestCoroutines()
    }


}