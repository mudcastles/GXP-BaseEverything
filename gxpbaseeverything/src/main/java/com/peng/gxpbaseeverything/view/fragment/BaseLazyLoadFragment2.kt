package com.peng.gxpbaseeverything.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.ImmersionFragment
import com.gyf.immersionbar.components.SimpleImmersionFragment
import com.peng.gxpbaseeverything.backKey.BackHandlerHelper
import com.peng.gxpbaseeverything.backKey.FragmentBackHandler


/**
 * Fragment预加载问题的解决方案：
 * 1.可以懒加载的Fragment
 * 2.切换到其他页面时停止加载数据（可选）
 *
 * 支持沉浸
 */

abstract class BaseLazyLoadFragment2 : ImmersionFragment(), FragmentBackHandler {
    private val TAG = "BaseLazyLoadFragment"



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "onViewCreated   ${javaClass.simpleName}   ${this.hashCode()}")
        super.onViewCreated(view, savedInstanceState)
        fitsLayoutOverlap()
        initView()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        //旋转屏幕为什么要重新设置布局与状态栏重叠呢？因为旋转屏幕有可能使状态栏高度不一样，如果你是使用的静态方法修复的，所以要重新调用修复
        fitsLayoutOverlap()
    }


    private var toast: Toast? = null
    fun showToast(message: String?) {
        if (toast == null)
            toast =
                Toast.makeText(this.requireActivity().applicationContext, "", Toast.LENGTH_SHORT)
        toast!!.setText(message)
        toast!!.show()
    }


    //return false表示不处理back点击事件
    override fun onBackPressed(): Boolean {
        return BackHandlerHelper.handleBackPress(this)
    }

    private fun fitsLayoutOverlap() {
        if (getStatusBarView() != null) {
            ImmersionBar.setStatusBarView(this, getStatusBarView())
        } else {
            ImmersionBar.setTitleBar(this, getTitleBarView())
        }
    }

    abstract fun getLayout(): Int

    /**
     * 第一次初始化的时候会调用
     */
    abstract fun initView()

    /**
     * 获取状态栏View，开发者自己实现
     */
    protected open fun getStatusBarView(): View? = null

    /**
     * 获取标题栏View，开发者自己实现
     */
    protected open fun getTitleBarView(): View? = null

    override fun initImmersionBar() {
        ImmersionBar.with(this).keyboardEnable(true).init()
    }
}