package com.example.gxp_baseeverything

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gxp_baseeverything.databinding.ActivityMainBinding
import com.example.gxp_baseeverything.databinding.DrawstartBinding
import com.peng.gxpbaseeverything.view.activity.mvvm.GXPToolbarDrawerActivity

class MainActivity :
    GXPToolbarDrawerActivity<ActivityMainBinding, DrawstartBinding, ViewDataBinding>() {
    private lateinit var mViewModel: MyModel
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

