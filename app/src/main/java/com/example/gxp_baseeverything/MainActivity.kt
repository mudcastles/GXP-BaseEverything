package com.example.gxp_baseeverything

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gxp_baseeverything.databinding.ActivityMainBinding
import com.example.gxp_baseeverything.databinding.DrawstartBinding
import com.example.gxp_baseeverything.model.*
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.peng.gxpbaseeverything.util.AndroidVersionAdaptUtil
import com.peng.gxpbaseeverything.view.activity.mvvm.GXPToolbarDrawerActivity
import java.util.jar.Manifest

class MainActivity :
    GXPToolbarDrawerActivity<ActivityMainBinding, DrawstartBinding, ViewDataBinding>() {
    private lateinit var mViewModel: MyModel
    override fun initData() {
        super.initData()
        if (AndroidVersionAdaptUtil.isLegacyExternalStorage()) {
            //Android 不使用分区存储时需要获取权限
            XXPermissions.with(this).permission(Permission.WRITE_EXTERNAL_STORAGE)
                .request { permissions, all ->

                }
        }
    }

    override fun onContentLayoutInflated() {
        super.onContentLayoutInflated()
        //这才是正确用法
        mViewModel = ViewModelProvider(
            application()!!,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(MyModel::class.java)


        //这样不能保证ViewModel的唯一性，每次都会创建一个新的
//        mViewModel =
//            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//                .create(MyModel::class.java)
        mBinding.viewModel = mViewModel

        mViewModel.mString.observe(this,
            Observer<String> {
                Log.e("mString数据变化了", "${mViewModel.mString.value ?: "......."}")
            })
        mViewModel.mEdittextStr.observe(this, Observer {
            Log.e("mEdittextStr数据变化了", "${mViewModel.mEdittextStr.value ?: "......."}")
        })
        mViewModel.mString.value = "呵呵哒lmklllkkl"


        //测试自定义view的双向绑定
        val model = MultiModel(First(Second(Third(Fourth(null)))), null)
        mViewModel.mModel.value = model
        mViewModel.mModel.observe(this, Observer {
            Log.e("自定义View双向绑定", it.first.second.third.fourth.string ?: "null")
        })
        mViewModel.mCheckBoxChecked.observe(this, Observer {
            Log.e("checkBox", it.toString())
        })

    }


    override fun getStartDrawerLayoutId(): Int? =
        R.layout.drawstart

    override fun getEndDrawerLayoutId(): Int? = null

    override fun onStartDrawerLayoutInflated() {
        super.onStartDrawerLayoutInflated()
        mStartDrawerBinding.string = "start drawer"
    }

    override fun getContentLayoutId(): Int =
        R.layout.activity_main
}

