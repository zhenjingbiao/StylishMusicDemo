package com.example.jingbiaozhen.stylishmusicdemo;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/*
 * Created by jingbiaozhen on 2018/1/24.
 **/

public class MusicApplication extends Application
{
    private static MusicApplication mMusicApplication;

    @Override
    public void onCreate()
    {
        super.onCreate();
        mMusicApplication = this;
        CalligraphyConfig.initDefault(
                new CalligraphyConfig.Builder().setDefaultFontPath("fonts/Roboto-Monospace-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());

    }

    public static MusicApplication getInstance()
    {
        return mMusicApplication;
    }

}
