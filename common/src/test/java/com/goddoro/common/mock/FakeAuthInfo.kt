package com.goddoro.common.mock

import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
//import kotlinx.coroutines.test.TestCoroutineScope
import kotlin.coroutines.CoroutineContext
import kotlin.random.Random

object FakeAuthInfo {

    val email = "goddoro@naver.com"
    val name = "goddoro"
    val password = "gusgh0705!"
    val acceptedLegalNoticeVersion = "2020-05-21"

    val unverifiedEmail = "${Random.nextInt()}@Test.com"
    val snsEmail = "goddoro@naver.com"
    val firebaseUid = "Qd6kNYbZnPXmlBnw5oTc0zIzcih2"

    val coverUrl = "https://cdn.staging.beatflo.co/users/cover_default.png"
    val avatar = "https://cdn.staging.beatflo.co/users/avatar_default.png"

}
interface ManagedCoroutineScope : CoroutineScope {
    abstract fun launch(block: suspend CoroutineScope.() -> Unit) : Job
}

class LifecycleManagedCoroutineScope(val lifecycleCoroutineScope: LifecycleCoroutineScope,
                                     override val coroutineContext: CoroutineContext): ManagedCoroutineScope {
    override fun launch(block: suspend CoroutineScope.() -> Unit): Job = lifecycleCoroutineScope.launchWhenStarted(block)
}

//class TestScope(override val coroutineContext: CoroutineContext) : ManagedCoroutineScope {
//    val scope = TestCoroutineScope(coroutineContext)
//    override fun launch(block: suspend CoroutineScope.() -> Unit): Job {
//        return scope.launch {
//            block.invoke(this)
//        }
//    }
//}