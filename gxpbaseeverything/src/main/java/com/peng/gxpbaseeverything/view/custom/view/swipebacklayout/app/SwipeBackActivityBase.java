package com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.app;


import com.peng.gxpbaseeverything.view.custom.view.swipebacklayout.SwipeBackLayout;

public interface SwipeBackActivityBase {
    /**
     * @return the SwipeBackLayout associated with this activity.
     */
    public abstract SwipeBackLayout getSwipeBackLayout();

    /**
     * Scroll out contentView and finish the activity
     */
    public abstract void scrollToFinishActivity();

    public abstract boolean isSwipeBackEnable();
}
