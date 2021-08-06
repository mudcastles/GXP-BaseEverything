package com.example.gxp_baseeverything;

import com.peng.gxpbaseeverything.util.CrashHandler;
import com.peng.gxpbaseeverything.view.GXPBaseApplication;

public class MyApplication extends GXPBaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.getInstance().init(getApplicationContext());
    }
}
