package com.peng.gxpbaseeverything.view.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.components.SimpleImmersionFragment
import com.peng.gxpbaseeverything.backKey.BackHandlerHelper
import com.peng.gxpbaseeverything.backKey.FragmentBackHandler
import com.peng.gxpbaseeverything.util.LoadingDialogHelper
import com.peng.gxpbaseeverything.util.RxRetrofitHelper
import com.peng.gxpbaseeverything.view.custom.view.LoadingDialog
import io.reactivex.rxjava3.disposables.CompositeDisposable

/**
 * 不支持懒加载的Fragment基类
 * 支持沉浸
 */
//没有处理back键需求的Fragment不用实现onBackPressed方法
abstract class BaseFragment : SimpleImmersionFragment(), FragmentBackHandler, RxRetrofitHelper,
    LoadingDialogHelper {
    private var TAG = "BaseFragment"
    override var mCompositeDisposable: CompositeDisposable? = null
    override var dialog: LoadingDialog? = null
    override var mContext: AppCompatActivity? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayout(), container, false)
    }

    abstract fun getLayout(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e(TAG, "onViewCreated   ${javaClass.simpleName}   ${this.hashCode()}")
        super.onViewCreated(view, savedInstanceState)
        mContext = requireActivity() as AppCompatActivity
        fitsLayoutOverlap()
        initImmersionBar()
        initData()
        initView()
        setListener()
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

    override fun onDestroyView() {
        dispose()
        super.onDestroyView()
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

    /**
     * 初始化数据
     */
    protected open fun initData() {}

    /**
     * view与数据绑定
     */
    protected open fun initView() {}

    /**
     * 设置监听
     */
    protected open fun setListener() {}


    /**
     * 获取状态栏View
     */
    protected open fun getStatusBarView(): View? = null

    /**
     * 获取标题栏View
     */
    protected open fun getTitleBarView(): View? = null

    override fun initImmersionBar() {
//        ImmersionBar.with(this).keyboardEnable(true).init()
    }
}