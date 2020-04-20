package com.example.gxp_baseeverything

import androidx.lifecycle.ViewModelProvider
import com.example.gxp_baseeverything.databinding.ActivitySecondBinding
import com.peng.gxpbaseeverything.view.activity.mvvm.GXPToolbarActivity

class SecondActivity : GXPToolbarActivity<ActivitySecondBinding>() {
    private lateinit var viewModel: MyModel

    override fun getContentLayoutId(): Int =
        R.layout.activity_second

    override fun onContentLayoutInflated() {
        super.onContentLayoutInflated()
        viewModel = ViewModelProvider(
            application()!!,
            ViewModelProvider.AndroidViewModelFactory(application)
        ).get(MyModel::class.java)
        mBinding.viewModel = viewModel
    }
}
