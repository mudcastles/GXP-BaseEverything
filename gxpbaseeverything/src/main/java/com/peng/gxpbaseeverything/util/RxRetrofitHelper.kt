package com.peng.gxpbaseeverything.util

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable


interface RxRetrofitHelper {
    var mCompositeDisposable: CompositeDisposable?
    fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    fun removeDisposable(disposable: Disposable) {
        mCompositeDisposable?.remove(disposable)
    }

    //在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
    fun dispose() {
        if (mCompositeDisposable != null && !mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable!!.dispose()
            mCompositeDisposable = null
        }
    }
}