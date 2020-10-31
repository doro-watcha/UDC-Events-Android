package com.goddoro.common

import io.reactivex.subjects.PublishSubject

object Broadcast {

    val eventUploadBroadcast : PublishSubject<Unit> = PublishSubject.create()
}