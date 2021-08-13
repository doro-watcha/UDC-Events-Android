package com.goddoro.common

import com.goddoro.common.data.model.Academy
import com.goddoro.common.data.model.Genre
import com.goddoro.common.data.model.Location
import io.reactivex.subjects.PublishSubject

object Broadcast {

    val eventUploadBroadcast : PublishSubject<String> = PublishSubject.create()


    val findAddressBroadcast : PublishSubject<Location> = PublishSubject.create()

    val bottomIndexChangeBroadcast : PublishSubject<Int> = PublishSubject.create()

    val profileGoTopBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val profileImageUpdateBroadcast : PublishSubject<Unit> = PublishSubject.create()

    val pickAcademyBroadcast : PublishSubject<Academy> = PublishSubject.create()

    val pickGenreBroadcast : PublishSubject<Genre> = PublishSubject.create()

    val pickLevelBroadcast : PublishSubject<String> = PublishSubject.create()

    val pickScheduleBroadcast : PublishSubject<String> = PublishSubject.create()

    val registerAcademyCompleteBroadcast : PublishSubject<String> = PublishSubject.create()

    val starClassBroadcast : PublishSubject<Float> = PublishSubject.create()
}