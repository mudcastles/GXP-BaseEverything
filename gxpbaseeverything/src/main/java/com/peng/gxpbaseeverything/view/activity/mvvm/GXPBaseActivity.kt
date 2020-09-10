package com.peng.gxpbaseeverything.view.activity.mvvm

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.peng.gxpbaseeverything.eventBus.MessageEvent
import com.peng.gxpbaseeverything.util.SomeCompat
import com.peng.gxpbaseeverything.view.GXPBaseApplication
import com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.app.SwipeBackActivity
import com.tbruyelle.rxpermissions3.RxPermissions
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


abstract class GXPBaseActivity<T : ViewDataBinding> : SwipeBackActivity() {
    internal var mActivity: Activity? = null

    /**
     * 布局的DataBinding对象
     */
    lateinit var mBinding: T


    /** -------------------生命周期----------------------*/
    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()
        mActivity = this
        //注册EventBus，监听双击back键操作
        EventBus.getDefault().register(this)
        //初始化数据
        initData()
        //view与数据绑定
        initView()
        //初始化沉浸式
        initImmersionBar()
        //设置监听
        setListener()

    }

    override fun onDestroy() {
        //必须反注册EventBus，防止内存泄漏
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }


    /** -------------------按需求实现相关初始化方法----------------------*/
    open fun initDataBinding() {
        mBinding = DataBindingUtil.setContentView(this, getRootLayoutId())
        mBinding.lifecycleOwner = this
    }

    /**
     * 初始化数据，一般在这里做一些intent数据的接收
     */
    open fun initData() {}

    /**
     * 初始化View，一般在这里做一些view的设置
     */
    open fun initView() {}

    /**
     * 退出APP时回调
     */
    open fun exitApp() {}

    /**
     * 设置监听器
     */
    open fun setListener() {}

    /*默认非根Activity，用以判断back键点击时直接退出还是执行双击验证*/
    open fun isBaseActivity() = false


    /**
     * 子类设置布局Id
     *
     * @return the layout id
     */
    protected abstract fun getRootLayoutId(): Int

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    open fun initImmersionBar() { //设置共同沉浸式样式
    }

    /**
     * 如果使用了MVVM框架，建议使用GXPBaseApplication
     */
    open fun application(): GXPBaseApplication? =
        if (application is GXPBaseApplication) application as GXPBaseApplication else null

    /** -------------------Toast显示----------------------*/
    var toast: Toast? = null

    fun showToast(context: Context, content: String) {
        if (toast == null) {
            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT)
        }
        toast?.setText(content)
        toast?.show()
    }

    fun showToast(content: String) {
        if (toast == null) {
            toast = Toast.makeText(applicationContext, content, Toast.LENGTH_SHORT)
        }
        toast?.setText(content)
        toast?.show()
    }


    /** -------------------双击back键退出应用----------------------*/
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (isBaseActivity() && keyCode == KeyEvent.KEYCODE_BACK && event?.action == KeyEvent.ACTION_DOWN) {
            doubleClickExit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    var firstTime: Long = 0
    internal fun doubleClickExit(): Boolean {
        if (System.currentTimeMillis() - firstTime > 2000) {
            showToast(applicationContext, "再按一次退出应用")
            firstTime = System.currentTimeMillis()
        } else {
            EventBus.getDefault().post(
                MessageEvent(
                    MessageEvent.FINISH
                )
            )
            exitApp()
            return true
        }
        return false
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(messageEvent: MessageEvent) {
        when (messageEvent.getMessage()) {
            MessageEvent.FINISH -> finish()
        }
    }


    /** -------------------工具方法----------------------*/
    protected fun bindActionDrawable(actionView: ImageView, @DrawableRes drawable: Int) {
        actionView.setImageDrawable(SomeCompat.getImageDrawable(this@GXPBaseActivity, drawable))
    }

    protected fun polishDrawable(drawable: Drawable, @ColorRes color: Int) {
        SomeCompat.polishDrawable(this@GXPBaseActivity, drawable, color)
    }

    var rxPermissions: RxPermissions? = null

    /**
     * 检查权限，获取到权限后调用
     */
    @SuppressLint("CheckResult")
    fun checkPermissions(
        permissionList: List<String>,
        onHasPermissions: () -> Unit,
        onDefinedPermissiong: () -> Unit
    ) {
        if (rxPermissions == null) rxPermissions = RxPermissions(this)

        rxPermissions!!.request(* permissionList.toTypedArray())
            .subscribe {
                if (it) {
                    onHasPermissions.invoke()
                } else {
                    onDefinedPermissiong.invoke()
                }
            }
    }
}