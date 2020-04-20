package com.peng.gxpbaseeverything.viewModel

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import com.peng.gxpbaseeverything.util.ToastHelper

open class GXPBaseViewModel(application: Application) : AndroidViewModel(application), ToastHelper {
    override var toast: Toast? = null
    override var mContext: Context = application.applicationContext

}