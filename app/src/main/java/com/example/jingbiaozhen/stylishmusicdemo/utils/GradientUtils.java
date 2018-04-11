package com.example.jingbiaozhen.stylishmusicdemo.utils;
/*
 * Created by jingbiaozhen on 2018/1/25.
 * 用代码创建一个GradientDrawable
 *
 **/

import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;

public class GradientUtils {
    public static GradientDrawable create(@ColorInt int startColor, @ColorInt int endColor, int radius,
                                          @FloatRange(from = 0f,to = 1f)float centerX,
                                          @FloatRange(from = 0f,to = 1f)float centerY){
        GradientDrawable drawable=new GradientDrawable();
        drawable.setColors(new int[]{startColor,endColor});
        drawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        drawable.setCornerRadius(radius);
        drawable.setGradientCenter(centerX,centerY);
        return drawable;

    }




}
