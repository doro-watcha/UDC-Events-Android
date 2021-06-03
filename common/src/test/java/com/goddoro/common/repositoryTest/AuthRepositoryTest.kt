package com.goddoro.common.repositoryTest

import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.di.apiModule
import com.goddoro.common.di.repositoryModule
import com.goddoro.common.di.utilModule
import com.goddoro.common.mock.FakeAuthInfo
import com.goddoro.common.mock.fakeNetworkModule
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
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

    @get:Rule
    var coroutinesTestRule = CoroutineTestRule()


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
    }

    @Test
    fun `sign in with email`() = runBlocking {


        val result = repository.signIn(FakeAuthInfo.email, FakeAuthInfo.password)
        assertEquals(result.user.email, FakeAuthInfo.email)


    }


}