package com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.app;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.SwipeBackLayout;
import com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.Utils;

import java.lang.ref.WeakReference;

public class SwipeBackListenerActivityAdapter implements SwipeBackLayout.SwipeListenerEx {
    private final WeakReference<Activity> mActivity;

    public SwipeBackListenerActivityAdapter(@NonNull Activity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void onScrollStateChange(int state, float scrollPercent) {

    }

    @Override
    public void onEdgeTouch(int edgeFlag) {
        Activity activity = mActivity.get();
        if (null != activity) {
            Utils.convertActivityToTranslucent(activity);
        }
    }

    @Override
    public void onScrollOverThreshold() {

    }

    @Override
    public void onContentViewSwipedBack() {
        Activity activity = mActivity.get();
        if (null != activity && !activity.isFinishing()) {
            activity.finish();
            activity.overridePendingTransition(0, 0);
        }
    }
}
