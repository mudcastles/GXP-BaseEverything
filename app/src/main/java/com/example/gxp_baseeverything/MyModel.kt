package com.example.gxp_baseeverything

import android.app.Application
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class MyModel(application: Application) : AndroidViewModel(application) {

    val mString: MutableLiveData<String> = MutableLiveData()
    val mEdittextStr: MutableLiveData<String> = MutableLiveData()
    override fun onCleared() {
        super.onCleared()
        Log.e("MyModel", "onCleared-------------------------")
    }

    fun click(view: View) {
        getApplication<Application>().startActivity(
            Intent(
                getApplication(),
                SecondActivity::class.java
            )
        )
    }
}