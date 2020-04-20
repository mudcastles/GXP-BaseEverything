package com.peng.gxpbaseeverything.view.activity.mvvm

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.gyf.immersionbar.ImmersionBar
import com.peng.gxpbaseeverything.R
import kotlinx.android.synthetic.main.gxp_layout_toolbar.*
import kotlinx.android.synthetic.main.mvvm_gxp_activity_toolbar.*


abstract class GXPToolbarActivity<T : ViewDataBinding> : GXPBaseActivity<T>() {
    final override fun getRootLayoutId(): Int = R.layout.mvvm_gxp_activity_toolbar

    /** -------------------生命周期----------------------*/
    /**
     * 设置内容部分是否要延伸到StatusBar和ActionBar
     */
    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        //如果内容不延伸到actionBar以及statusBar，则应设置marginTop
        if (!isContentExtendToStatusBar()) {
            val contentView = com_peng_baseeverthing_root_view.getChildAt(0)
            val params = contentView.layoutParams as FrameLayout.LayoutParams
            params.topMargin =
                ImmersionBar.getStatusBarHeight(this) + ImmersionBar.getActionBarHeight(this)
            contentView.layoutParams = params
        }

        initToolbar()
    }

    /** -------------------按需求实现相关初始化方法----------------------*/
    /**内容部分是否延伸到statusBar，true，不需要做任何操作，false，需要为根布局设置padding或marginTop*/
    open fun isContentExtendToStatusBar() = false

    /**
     *  初始化toolbar，可以做一些toolbar的主副标题、返回按钮、menu等的设置
     */
    open fun initToolbar() {}

    /**
     * 子类设置布局Id
     *
     * @return the layout id
     */
    protected abstract fun getContentLayoutId(): Int

    /**
     * 初始化内容布局完成
     */
    open fun onContentLayoutInflated() {}

    /**
     * 不允许修改该方法
     */
    final override fun initView() {
        super.initView()
        if (actionBar == null)
            setSupportActionBar(actionBar())

        if (com_peng_baseeverthing_root_view.childCount == 1) { //确保横竖屏切换时只会新增一个内容布局
            mBinding = DataBindingUtil.bind<T>(
                LayoutInflater.from(this@GXPToolbarActivity)
                    .inflate(getContentLayoutId(), com_peng_baseeverthing_root_view, false)
            )!!
            com_peng_baseeverthing_root_view.addView(
                mBinding.root,
                0
            )
            mBinding.lifecycleOwner = this@GXPToolbarActivity
        }
        onContentLayoutInflated()
    }

    /**
     * 直接设置页面布局
     */
    final override fun initDataBinding() {
        setContentView(getRootLayoutId())
    }

    /**获取toolbar，来设置title、subtitle、appearance等*/
    final fun actionBar() = com_peng_baseeverthing_toolbar

    /**
     * 设置沉浸式
     */
    override fun initImmersionBar() {
        super.initImmersionBar()
        ImmersionBar.with(this).titleBar(R.id.com_peng_baseeverthing_toolbar).init()
    }


    /**
     * 启用toolbar中的左侧图标按钮，并用作返回键
     */
    fun enableLeftIcon() {
        enableLeftIcon(R.drawable.ic_back, object :
            OnToolbarIconClickListener {
            override fun onLeftIconClicked() {
                if (isBaseActivity()) {
                    doubleClickExit()
                } else {
                    finish()
                }
            }
        })
    }

    /**
     * 启用toolbar中的左侧图标按钮，自定义图标和点击回调
     */
    fun enableLeftIcon(@DrawableRes drawableRes: Int, function: OnToolbarIconClickListener) {
        com_peng_baseeverthing_toolbar.setNavigationIcon(drawableRes)
        com_peng_baseeverthing_toolbar.setNavigationOnClickListener {
            function.onLeftIconClicked()
        }
    }

    /**
     * 保存menu的资源
     */
    @MenuRes
    private var menuRes: Int? = null

    /**
     * 向toolbar添加menu并设置点击事件
     */
    fun addMenuIcons(
        @DrawableRes overflowIcon: Int?,
        @MenuRes menuRes: Int,
        menuItemClickListener: Toolbar.OnMenuItemClickListener
    ) {
        this.menuRes = menuRes
        //设置溢出图片  如果不设置会默认使用系统灰色的图标
        if (overflowIcon != null)
            com_peng_baseeverthing_toolbar.overflowIcon =
                ResourcesCompat.getDrawable(resources, overflowIcon, theme)
        //填充menu
        com_peng_baseeverthing_toolbar.inflateMenu(menuRes)
        //设置点击事件
        com_peng_baseeverthing_toolbar.setOnMenuItemClickListener(menuItemClickListener)
    }

    /** 创建OptionsMenu */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if (menuRes != null) {
            menuInflater.inflate(menuRes!!, menu)
            true
        } else super.onCreateOptionsMenu(menu)
    }

    /** 监听OptionsMenu的点击事件 */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    /**
     * 设置toolbar溢出菜单的样式
     */
    fun setToolbarPopupTheme(@StyleRes popupTheme: Int) {
        com_peng_baseeverthing_toolbar.popupTheme = popupTheme
    }

    /** -------------------接口----------------------*/
    /**
     * toolbar左侧按钮点击监听器
     */
    interface OnToolbarIconClickListener {
        fun onLeftIconClicked()
    }
}