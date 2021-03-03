package com.peng.gxpbaseeverything.util

import androidx.appcompat.app.AppCompatActivity
import com.peng.gxpbaseeverything.view.custom.view.LoadingDialog

interface LoadingDialogHelper {
    var dialog: LoadingDialog?
    var mContext: AppCompatActivity?
    fun showLoading(message: String) {
        closeLoading()
        if (mContext != null) {
            dialog = LoadingDialog(mContext!!, message)
            dialog!!.show()
        }
    }

    fun closeLoading() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    fun updateMessage(message: String) {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.updateMessage(message)
        }
    }
}