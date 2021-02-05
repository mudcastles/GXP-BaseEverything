package com.peng.gxpbaseeverything.view.custom.view

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import com.peng.gxpbaseeverything.R

interface AlertDialog {
    //alert弹框的布局
    var mCommonAlertDialog: AlertDialog?
    var mCommonAlertDialogContentView: View?

    /**
     * 显示toast dialog
     *
     * @param message
     */
    open fun showToastDialog(context: Context,title: String?, message: String?) {
        showToastDialog(context,title, message, null, "知道了", null, null, null)
    }

    /**
     * 显示toast dialog.
     * 可以根据{@param buttonIdentifier}来标识显示哪个Button，可选值：{@link android.content.DialogInterface.BUTTON_POSITIVE}{@link android.content.DialogInterface.BUTTON_NEGATIVE}
     *
     * @param message
     * @param buttonText
     *
     * @param buttonIdentifier 按钮标识。
     * @see DialogInterface.BUTTON_POSITIVE
     * @see DialogInterface.BUTTON_NEGATIVE
     */
    open fun showToastDialog(
        context: Context,
        title: String?,
        message: String?,
        buttonText: String?,
        buttonTextColor: Int?,
        buttonIdentifier: Int
    ) {
        when (buttonIdentifier) {
            DialogInterface.BUTTON_POSITIVE -> {
                showToastDialog(
                    context,
                    title,
                    message,
                    buttonText,
                    null,
                    buttonTextColor,
                    null,
                    null
                )
            }
            DialogInterface.BUTTON_NEGATIVE -> {
                showToastDialog(
                    context,
                    title,
                    message,
                    null,
                    buttonText,
                    null,
                    buttonTextColor,
                    null
                )
            }
        }
    }

    /**
     * 显示toast dialog
     *
     * @param message
     * @param positiveText
     * @param onClickListener
     */
    open fun showToastDialog(
        context: Context,
        title: String?,
        message: String?,
        positiveText: String?,
        negativeText: String?,
        @ColorInt positiveTextColor: Int?,
        @ColorInt negativeTextColor: Int?,
        onButtonClickListener: DialogInterface.OnClickListener?
    ) {
        showAlertDialog(
            context,
            title,
            message,
            positiveText, negativeText,
            positiveTextColor, negativeTextColor,
            onButtonClickListener
        )
    }

    /**
     * 显示Alert Dialog
     * 注意：
     */
    fun showAlertDialog(
        context: Context,
        title: String?,
        message: String?,
        positiveText: String?,
        negativeText: String?,
        @ColorInt positiveTextColor: Int?,
        @ColorInt negativeTextColor: Int?,
        onButtonClickListener: DialogInterface.OnClickListener?
    ) {
        showAlertDialog(
            context,
            title,
            message,
            positiveText,
            negativeText,
            positiveTextColor,
            negativeTextColor,
            false,
            onButtonClickListener
        )
    }

    /**
     * 显示Alert Dialog
     * 注意：
     */
    fun showAlertDialog(
        context: Context,
        title: String?,
        message: String?,
        positiveText: String?,
        negativeText: String?,
        @ColorInt positiveTextColor: Int?,
        @ColorInt negativeTextColor: Int?,
        cancelable: Boolean,
        onButtonClickListener: DialogInterface.OnClickListener?
    ) {
        if (mCommonAlertDialogContentView == null || mCommonAlertDialog == null) {
            mCommonAlertDialogContentView =
                LayoutInflater.from(context).inflate(R.layout.dialog_common_alert, null)

            mCommonAlertDialog = AlertDialog.Builder(context)
                .setView(mCommonAlertDialogContentView)
                .setCancelable(cancelable)
                .create()
        }
        if (!mCommonAlertDialog!!.isShowing) {
            mCommonAlertDialog!!.show()
            mCommonAlertDialog!!.window?.attributes =
                mCommonAlertDialog!!.window?.attributes?.apply {
                    this.width =
                        context.resources.getDimensionPixelSize(R.dimen.common_alert_tips_width)
                }
        }

        mCommonAlertDialogContentView!!.findViewById<TextView>(R.id.common_alert_title).apply {
            setText(title)
            visibility = if (title.isNullOrBlank()) View.GONE else View.VISIBLE
        }
        mCommonAlertDialogContentView!!.findViewById<TextView>(R.id.common_alert_message)
            .apply {
                setText(message)
                visibility = if (message.isNullOrBlank()) View.GONE else View.VISIBLE
            }

        mCommonAlertDialogContentView!!.findViewById<TextView>(R.id.common_alert_position_button)
            .apply {
                if (!positiveText.isNullOrEmpty()) {
                    visibility = View.VISIBLE
                    text = positiveText
                    setTextColor(
                        positiveTextColor ?: ResourcesCompat.getColor(
                            context.resources,
                            R.color.common_dialog_btn_blue,
                            context.theme
                        )
                    )
                    setOnClickListener {
                        mCommonAlertDialog?.dismiss()
                        onButtonClickListener?.onClick(
                            mCommonAlertDialog,
                            DialogInterface.BUTTON_POSITIVE
                        )
                    }
                } else {
                    visibility = View.GONE
                }
            }
        mCommonAlertDialogContentView!!.findViewById<TextView>(R.id.common_alert_negative_button)
            .apply {
                if (!negativeText.isNullOrEmpty()) {
                    visibility = View.VISIBLE
                    text = negativeText
                    setTextColor(
                        negativeTextColor ?: ResourcesCompat.getColor(
                            context.resources,
                            R.color.common_dialog_btn_gray,
                            context.theme
                        )
                    )
                    setOnClickListener {
                        mCommonAlertDialog?.dismiss()
                        onButtonClickListener?.onClick(
                            mCommonAlertDialog,
                            DialogInterface.BUTTON_NEGATIVE
                        )
                    }
                } else {
                    visibility = View.GONE
                }
            }
    }
}