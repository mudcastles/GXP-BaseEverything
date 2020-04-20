package com.peng.gxpbaseeverything.util

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface RxRetrofitHelper {
    var mCompositeDisposable: CompositeDisposable?
    fun addDisposable(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        mCompositeDisposable!!.add(disposable)
    }

    //在界面退出等需要解绑观察者的情况下调用此方法统一解绑，防止Rx造成的内存泄漏
    fun dispose() {
        if (mCompositeDisposable != null && !mCompositeDisposable!!.isDisposed) {
            mCompositeDisposable!!.dispose()
            mCompositeDisposable = null
        }
    }
}