package com.goddoro.common.repository


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.goddoro.common.mock.FakeAuthInfo
import com.goddoro.common.mock.ManagedCoroutineScope
import com.goddoro.common.mock.TestScope
import com.goddoro.common.mock.fakeNetworkModule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


/**
 * created By DORO 2020/09/13
 */


class AuthViewModel : ViewModel() {
    private var likeCount = 0


    fun login () {
        viewModelScope.launch {
            kotlin.runCatching {
                fakeNetworkModule.authService.signIn(FakeAuthInfo.email, FakeAuthInfo.password)
            }.onSuccess{

            }.onFailure {

            }
        }
    }
}

@RunWith(MockitoJUnitRunner::class)
class AuthService {



    @ExperimentalCoroutinesApi
    private val testDispatcher = TestCoroutineDispatcher()
    private val managedCoroutineScope: ManagedCoroutineScope = TestScope(testDispatcher)

    @Before
    fun setup() {

        Dispatchers.setMain(testDispatcher)

    }


    @Test
    fun `login` () {

        runBlockingTest{

        }
        val articleViewModel = AuthViewModel()


        Assert.assertNotEquals(1, articleViewModel.login())
    }

}