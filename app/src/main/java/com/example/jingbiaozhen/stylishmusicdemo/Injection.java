package com.example.jingbiaozhen.stylishmusicdemo;
/*
 * Created by jingbiaozhen on 2018/2/2.
 **/

import android.content.Context;

public class Injection {
    public static Context provideContext(){
        return MusicApplication.getInstance();
    }
}
