package com.peng.gxpbaseeverything.view.custom.view

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import com.peng.gxpbaseeverything.R
import kotlinx.android.synthetic.main.dialog_loading.*

class LoadingDialog(
    private val windowContext: Context,
    private val msg: String?
) : Dialog(windowContext, R.style.CustomProgressDialog) {

    init {
        setContentView(R.layout.dialog_loading)
        window?.attributes?.gravity = Gravity.CENTER
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    override fun show() {
        super.show()
        tv_loading_txt.text = msg
    }

    override fun dismiss() {
        loadingView.visibility = View.GONE
        super.dismiss()
    }

    fun updateMessage(newMessage: String) {
        tv_loading_txt.text = newMessage
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) {
            dismiss()
        }
    }

}