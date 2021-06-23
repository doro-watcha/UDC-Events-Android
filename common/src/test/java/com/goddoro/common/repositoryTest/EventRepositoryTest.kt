package com.goddoro.common.repositoryTest

import androidx.test.core.app.ApplicationProvider
import androidx.test.filters.LargeTest
import com.goddoro.common.common.toUri
import com.goddoro.common.data.repository.AuthRepository
import com.goddoro.common.data.repository.EventRepository
import com.goddoro.common.di.apiModule
import com.goddoro.common.di.repositoryModule
import com.goddoro.common.di.utilModule
import com.goddoro.common.mock.FakeAuthInfo
import com.goddoro.common.mock.fakeNetworkModule
import io.reactivex.Completable
import junit.framework.Assert
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
class EventRepositoryTest : AutoCloseKoinTest() {

    private val TAG = EventRepositoryTest::class.java.simpleName

    private lateinit var repository: EventRepository

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
    fun `upload event` () = runBlocking {


        repository.uploadEvent(
            name = "Uplaod Test",
            posterImg = "content://media/external/images/media/54".toUri(),
            description = "zxcv",
            date = "2021-06-23 오후 10:36",
            location = "zxcv",
            eventType = "배틀",
            subTitle = "zxcvzxcv"
        )
        Assert.assertEquals(1,1)
    }

}