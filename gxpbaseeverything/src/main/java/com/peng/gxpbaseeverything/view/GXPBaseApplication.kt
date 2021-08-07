package com.peng.gxpbaseeverything.view

import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.multidex.MultiDexApplication
import com.hjq.permissions.XXPermissions
import com.peng.gxpbaseeverything.util.AndroidVersionAdaptUtil

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

    override fun onCreate() {
        super.onCreate()
        XXPermissions.setScopedStorage(!AndroidVersionAdaptUtil.isLegacyExternalStorage())
    }
}