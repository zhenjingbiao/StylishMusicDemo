package com.example.jingbiaozhen.stylishmusicdemo.data.source;
/*
 * Created by jingbiaozhen on 2018/2/5.
 **/

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceManager {
    private static final String PREFS_NAME="config.xml";
    private static final String KEY_FOLDERS_FIRST_QUERY = "firstQueryFolder";

    private static SharedPreferences preferences(Context context){
        return context.getSharedPreferences(PREFS_NAME,Context.MODE_PRIVATE);
    }
    private static SharedPreferences.Editor edit(Context context){
        return preferences(context).edit();
    }

    public static boolean isFirstQueryFolders(Context context){
        return preferences(context).getBoolean(KEY_FOLDERS_FIRST_QUERY,true);
    }
    public static void reportFirstQueryFolders(Context context){
        edit(context).putBoolean(KEY_FOLDERS_FIRST_QUERY,false).commit();
    }



}
