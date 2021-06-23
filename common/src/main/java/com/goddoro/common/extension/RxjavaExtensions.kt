package com.goddoro.common.extension

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


/**
 * created By DORO 2020/10/31
 */

fun <T> Single<T>.addSchedulers(): Single<T> =
    subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())


fun <T> Single<T>.addComputationSchedulers(): Single<T> =
    subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.addSchedulers(): Maybe<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.addSchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.addSchedulers(): Flowable<T> {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.addSchedulers(): Completable {
    return this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}

fun Completable.addComputationSchedulers(): Completable {
    return this.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread())
}

fun rxSingleTimer(milliseconds: Long, completion: (t: Long) -> Unit): Disposable {
    return Single.timer(milliseconds, TimeUnit.MILLISECONDS)
        .addSchedulers()
        .subscribe(completion)
}

fun rxSimpleComputationRun(run: () -> Unit): Disposable {
    return Completable.fromCallable(run)
        .addComputationSchedulers()
        .subscribe()
}

fun rxSimpleRun(run: () -> Unit): Disposable {
    return Completable.fromCallable(run)
        .addSchedulers()
        .subscribe()
}

fun rxRepeatTimer(tick: Long, onNext: (t: Long) -> Unit, initialDelay : Long? = 0): Disposable {
    return Observable.interval(initialDelay ?: 0L, tick, TimeUnit.MILLISECONDS)
        .addSchedulers()
        .subscribe({
            onNext(it)
        }, {

        })
}


fun Disposable.disposedBy(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}