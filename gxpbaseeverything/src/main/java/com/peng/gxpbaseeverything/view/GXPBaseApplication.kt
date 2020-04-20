package com.peng.gxpbaseeverything.view

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication

/**
 * 如果使用了MVVM框架，建议使用GXPBaseApplication
 */
open class GXPBaseApplication : MultiDexApplication(), ViewModelStoreOwner {
    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }
}