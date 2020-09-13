package com.goddoro.common.mock

import com.goddoro.common.repository.AuthService
import dagger.Component
import javax.inject.Singleton


/**
 * created By DORO 2020/09/13
 */

@Singleton
@Component(
    modules = [
        (fakeNetworkModule::class)
    ]
)
interface ApplicationComponent {

}