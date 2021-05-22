package com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.app;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.SwipeBackLayout;
import com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.Utils;


public class SwipeBackActivity extends AppCompatActivity implements SwipeBackActivityBase {
    private SwipeBackActivityHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSwipeBackEnable()) {
            mHelper = new SwipeBackActivityHelper(this);
            mHelper.onActivityCreate();
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (isSwipeBackEnable()) {
            mHelper.onPostCreate();
        }
    }

    @Override
    public View findViewById(int id) {
        View v = super.findViewById(id);
        if (v == null && mHelper != null)
            return mHelper.findViewById(id);
        return v;
    }

    @Override
    public SwipeBackLayout getSwipeBackLayout() {
        if (mHelper != null)
            return mHelper.getSwipeBackLayout();
        else return null;
    }

    @Override
    public boolean isSwipeBackEnable() {
        return true;
    }

    @Override
    public void scrollToFinishActivity() {
        if (isSwipeBackEnable()) {
            Utils.convertActivityToTranslucent(this);
            getSwipeBackLayout().scrollToFinishActivity();
        }
    }
}
