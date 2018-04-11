package com.example.jingbiaozhen.stylishmusicdemo.ui.common;
/*
 * Created by jingbiaozhen on 2018/2/1.
 * RecyclerView默认的分割线
 **/

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class DefaultDividerDecoration extends RecyclerView.ItemDecoration {
    private static final int DIVIDER_HEIGHT = 1;//默认设置为一个像素

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.bottom=DIVIDER_HEIGHT;
    }
}
