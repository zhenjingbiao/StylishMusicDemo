package com.example.jingbiaozhen.stylishmusicdemo.ui.base;
/*
 * Created by jingbiaozhen on 2018/1/26.
 * 将Fragment的宽缩小为原来的0.85倍
 *
 **/

import android.graphics.Point;
import android.support.v4.app.DialogFragment;
import android.view.Window;
import android.view.WindowManager;

public class BaseDialogFragment extends DialogFragment
{
    private static final float PROPORTION = 0.85f;

    protected void resizeDialogSize()
    {
        Window window = getDialog().getWindow();
        Point point = new Point();
        window.getWindowManager().getDefaultDisplay().getSize(point);
        window.setLayout((int) (point.x * PROPORTION), WindowManager.LayoutParams.WRAP_CONTENT);
    }

}
