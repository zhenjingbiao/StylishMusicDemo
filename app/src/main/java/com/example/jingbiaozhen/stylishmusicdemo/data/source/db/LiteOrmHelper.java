package com.example.jingbiaozhen.stylishmusicdemo.data.source.db;
/*
 * Created by jingbiaozhen on 2018/2/6.
 * 单例获取LiteOrm
 **/

import com.example.jingbiaozhen.stylishmusicdemo.BuildConfig;
import com.example.jingbiaozhen.stylishmusicdemo.Injection;
import com.litesuits.orm.LiteOrm;

public class LiteOrmHelper
{
    private static final String DB_NAME = "music-player.db";

    private static volatile LiteOrm sInstance;

    private LiteOrmHelper()
    {

    }

    public static LiteOrm getInstance()
    {
        if (sInstance == null)
        {
            synchronized (LiteOrmHelper.class)
            {
                if (sInstance == null)
                {
                    sInstance = LiteOrm.newCascadeInstance(Injection.provideContext(), DB_NAME);
                    sInstance.setDebugged(BuildConfig.DEBUG);

                }
            }
        }
        return sInstance;
    }
}
