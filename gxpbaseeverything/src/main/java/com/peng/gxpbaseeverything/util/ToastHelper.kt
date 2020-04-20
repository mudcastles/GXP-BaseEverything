package com.peng.gxpbaseeverything.util

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

interface ToastHelper {
    var toast: Toast?
    var mContext: Context

    @SuppressLint("ShowToast")
    fun showToast(message: String) {
        if (toast == null) {    //避免创建多个toast造成提示延迟和大量消耗内存
            toast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT)
        }
        toast!!.setText(message)
        toast!!.show()   //必须调用show()方法才能显示提示
    }
}