package com.peng.gxpbaseeverything.util.recyclerView

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.peng.gxpbaseeverything.R

class VerticalSpaceDividerDecoration: RecyclerView.ItemDecoration {
    private var dividerHeight: Int = 1
    private lateinit var mContext: Context

    constructor(mContext: Context) {
        this.mContext =mContext
        dividerHeight = mContext.resources.getDimensionPixelSize(R.dimen.divider_height)
    }

    constructor(mContext: Context, dividerHeight: Int) {
        this.mContext =mContext
        this.dividerHeight = dividerHeight
    }



    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.set(0, 0, 0, dividerHeight)
    }
}