package com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.app;


import com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.SwipeBackLayout;

public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    public abstract void setSwipeBackEnable(boolean enable);

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();
}
