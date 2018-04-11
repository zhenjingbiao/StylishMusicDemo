package com.example.jingbiaozhen.stylishmusicdemo.utils;
/*
 * Created by jingbiaozhen on 2018/2/12.
 **/

import android.annotation.SuppressLint;

public class TimeUtils
{

    @SuppressLint("DefaultLocale")
    public static String formDuration(int duration)
    {
        duration /= 1000;// 转换成秒
        int minute = duration / 60;
        int hour = minute / 60;
        minute %= 60;
        int second = duration % 60;
        if (hour != 0)
        {
            return String.format("%2d:%02d:%02d", hour, minute, second);
        }
        else
        {
            return String.format("%02d:%02d", hour, second);
        }

    }
}
