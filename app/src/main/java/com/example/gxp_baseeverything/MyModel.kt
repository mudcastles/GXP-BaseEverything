package com.example.gxp_baseeverything

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.gxp_baseeverything.model.MultiModel

class MyModel(application: Application) : AndroidViewModel(application) {

    val mString: MutableLiveData<String> = MutableLiveData()
    val mEdittextStr: MutableLiveData<String> = MutableLiveData()
    val mModel:MutableLiveData<MultiModel> = MutableLiveData()
    val mCheckBoxChecked = MutableLiveData<Boolean>()
    override fun onCleared() {
        super.onCleared()
        Log.e("MyModel", "onCleared-------------------------")
    }

    fun click(view: View) {
        getApplication<Application>().startActivity(
            Intent(
                getApplication(),
                SecondActivity::class.java
            ).apply {
                addFlags(FLAG_ACTIVITY_NEW_TASK)
            }
        )
    }

    fun changeValueCheckbox(){
        if (mModel.value!=null){
            mModel.value!!.first.second.third.fourth.string = "true"
            mModel.value!!.string = "true"
        }
    }
}