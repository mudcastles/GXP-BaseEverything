package com.peng.gxpbaseeverything.view.activity.mvvm

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.drawerlayout.widget.DrawerLayout
import com.peng.gxpbaseeverything.R
import kotlinx.android.synthetic.main.mvvm_gxp_activity_drawer.*

abstract class GXPDrawerActivity<T : ViewDataBinding, START_DRAWER : ViewDataBinding, END_DRAWER : ViewDataBinding> :
    GXPBaseActivity<T>() {

    /**
     * 内容部分的View
     */
    private lateinit var mContentView: View

    /**
     * 左侧（Start）菜单View
     */
    private lateinit var mStartDrawerView: View

    /**
     * 右侧（End）菜单View
     */
    private lateinit var mEndDrawerView: View

    /**
     * Start菜单的DataBinding对象
     */
    lateinit var mStartDrawerBinding: START_DRAWER

    /**
     * End菜单的DataBinding对象
     */
    lateinit var mEndDrawerBinding: END_DRAWER

    /**
     * 直接返回外层布局
     */
    final override fun getRootLayoutId(): Int = R.layout.mvvm_gxp_activity_drawer

    /**
     * 向DrawerLayout添加内容布局并初始化内容DataBinding对象
     */
    override fun initDataBinding() {
        setContentView(getRootLayoutId())
        mContentView = LayoutInflater.from(this)
            .inflate(getContentRootLayoutId(), com_peng_baseeverthing_drawerLayout, false)
        if (!com_peng_baseeverthing_drawerLayout.children.contains(mContentView)) { //确保横竖屏切换时只会新增一个内容布局
            mBinding = DataBindingUtil.bind<T>(mContentView)!!
            com_peng_baseeverthing_drawerLayout.addView(
                mBinding.root,
                0
            )
            mBinding.lifecycleOwner = this
        }
        onContentLayoutInflated()
    }

    /**
     * 向DrawerLayout添加Start菜单布局并初始化Start菜单的DataBinding对象
     */
    private fun initStartDrawerDataBinding() {
        if (getStartDrawerLayoutId() == null) return
        mStartDrawerView = LayoutInflater.from(this)
            .inflate(getStartDrawerLayoutId()!!, com_peng_baseeverthing_drawerLayout, false)
        if (!com_peng_baseeverthing_drawerLayout.children.contains(mStartDrawerView)) { //确保横竖屏切换时只会新增一个内容布局
            mStartDrawerBinding = DataBindingUtil.bind<START_DRAWER>(mStartDrawerView)!!
            com_peng_baseeverthing_drawerLayout.addView(
                mStartDrawerBinding.root,
                (mStartDrawerBinding.root.layoutParams as DrawerLayout.LayoutParams).apply {
                    gravity = Gravity.START
                }
            )
            mStartDrawerBinding.lifecycleOwner = this
        }
        onStartDrawerLayoutInflated()
    }

    /**
     * 向DrawerLayout添加End菜单布局并初始化End菜单的DataBinding对象
     */
    private fun initEndDrawerDataBinding() {
        if (getEndDrawerLayoutId() == null) return
        mEndDrawerView = LayoutInflater.from(this)
            .inflate(getEndDrawerLayoutId()!!, com_peng_baseeverthing_drawerLayout, false)
        if (!com_peng_baseeverthing_drawerLayout.children.contains(mEndDrawerView)) { //确保横竖屏切换时只会新增一个内容布局
            mEndDrawerBinding = DataBindingUtil.bind<END_DRAWER>(mEndDrawerView)!!
            com_peng_baseeverthing_drawerLayout.addView(
                mEndDrawerBinding.root,
                (mEndDrawerBinding.root.layoutParams as DrawerLayout.LayoutParams).apply {
                    gravity = Gravity.END
                }
            )
            mEndDrawerBinding.lifecycleOwner = this
        }
        onEndDrawerLayoutInflated()
    }

    /**
     * 为了防止这部分代码被修改，不允许重写该方法
     */
    final override fun initView() {
        super.initView()
        setSwipeBackEnable(false)
        initStartDrawerDataBinding()
        initEndDrawerDataBinding()
    }


    /** -------------------按需求实现相关初始化方法----------------------*/
    /**
     * 内容部分的根布局
     */
    open abstract fun getContentRootLayoutId(): Int

    /**
     * Start菜单的布局
     */
    open abstract fun getStartDrawerLayoutId(): Int?

    /**
     * End菜单的布局
     */
    open abstract fun getEndDrawerLayoutId(): Int?

    /**
     * 把内容布局的View添加到外层布局后，且内容的DataBinding初始化完成后调用
     */
    open fun onContentLayoutInflated() {}

    /**
     * 把Start菜单布局的View添加到外层布局后，且Start菜单的DataBinding初始化完成后调用
     */
    open fun onStartDrawerLayoutInflated() {}

    /**
     * 把End菜单布局的View添加到外层布局后，且End菜单的DataBinding初始化完成后调用
     */
    open fun onEndDrawerLayoutInflated() {}
}