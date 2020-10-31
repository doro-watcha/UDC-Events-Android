package com.goddoro.common.common

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * created By DORO 2020/09/26
 */

fun Disposable.disposedBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}